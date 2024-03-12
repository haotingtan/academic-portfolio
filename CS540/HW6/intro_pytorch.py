import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from torchvision import datasets, transforms

# Feel free to import other packages, if needed.
# As long as they are supported by CSL machines.


def get_data_loader(training = True):
    custom_transform = transforms.Compose([
        transforms.ToTensor(),
        transforms.Normalize((0.1307,), (0.3081,))
        ])

    train_set = datasets.FashionMNIST('./ data', train=True, download=True, transform=custom_transform)
    test_set = datasets.FashionMNIST('./ data', train=False, transform=custom_transform)

    if training:
        return torch.utils.data.DataLoader(train_set, batch_size=64, shuffle=True)
    else:
        return torch.utils.data.DataLoader(test_set, batch_size=64, shuffle=False)


def build_model():
    model = nn.Sequential(
        nn.Flatten(),
        nn.Linear(in_features=28*28, out_features=128),
        nn.ReLU(),
        nn.Linear(in_features=128, out_features=64),
        nn.ReLU(),
        nn.Linear(in_features=64, out_features=10)
    )
    return model


def train_model(model, train_loader, criterion, T):
    optimizer = optim.SGD(model.parameters(), lr=0.001, momentum=0.9)

    model = model.train()
    for epoch in range(T):
        running_loss = 0.0
        running_corrects = 0
        total = 0

        for i, data in enumerate(train_loader):
            inputs, labels = data

            optimizer.zero_grad()
            outputs = model(inputs)
            _, predicted = torch.max(outputs.data, 1)
            loss = criterion(outputs, labels)
            loss.backward()
            optimizer.step()

            total += labels.size(0)
            running_loss += loss.item() * inputs.size(0)
            running_corrects += (predicted == labels).sum().item()

        print('Train Epoch: {}\tAccuracy: {}/{}({:.2f}%)\tLoss: {:.3f}'.format(epoch,
                running_corrects, total, 100.*running_corrects/total, running_loss/total))


def evaluate_model(model, test_loader, criterion, show_loss = True):
    model.eval()

    with torch.no_grad():
        running_loss = 0.0
        running_corrects = 0
        total = 0

        for data, labels in test_loader:
            outputs = model(data)
            _, predicted = torch.max(outputs.data, 1)
            loss = criterion(outputs, labels)

            running_loss += loss.item() * data.size(0)
            running_corrects += (predicted == labels).sum().item()
            total += data.size(0)

        if show_loss:
            print('Average loss: {:.4f}'.format(running_loss/total))
        print('Accuracy: {:.2f}%'.format(100.*running_corrects/total))


def predict_label(model, test_images, index):
    class_names = ['T-shirt/top','Trouser','Pullover','Dress','Coat','Sandal','Shirt','Sneaker','Bag','Ankle Boot']

    model.eval()
    with torch.no_grad():
        output = model(test_images[index])
        prob = F.softmax(output, dim=1)
        probs, labels = prob.topk(3)
        probs = probs.squeeze().tolist()
        labels = labels.squeeze().tolist()
        names_ranked = []
        for j in labels:
            names_ranked.append(class_names[j])

        for i in range(3):
            print("{}: {:.2f}%".format(names_ranked[i], 100.*probs[i]))


if __name__ == '__main__':
    '''
    Feel free to write your own test code here to exaime the correctness of your functions. 
    Note that this part will not be graded.
    '''
    criterion = nn.CrossEntropyLoss()

    # train_loader = get_data_loader()
    # test_loader = get_data_loader(training=False)
    # m = build_model()
    # train_model(m, train_loader, criterion, 5)
    # evaluate_model(m, test_loader, criterion, show_loss=False)
    # evaluate_model(m, test_loader, criterion, show_loss=True)
    #
    # test_imgs, test_labels = next(iter(test_loader))
    # predict_label(m, test_imgs, 1)
