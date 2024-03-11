import matplotlib.pyplot as plt

# x axis values
x = [1, 2, 4, 6, 8, 10, 12, 14, 16]
# corresponding y axis values
y = [9, 3.339577, 2.201892, 2.386068, 2.223459, 2.642628, 2.546077, 2.368777, 2.435231]

fig, ax = plt.subplots()

# plotting the points
ax.plot(x, y)

# naming the x axis
plt.xlabel('number of threads')
# naming the y axis
plt.ylabel('Execution time (s)')

plt.title('Execution time (s) vs. numThreads')

# giving a title to my graph
ax.annotate('Input size of the file: 1GB', xy=(6
                                               , 8))

# function to show the plot
plt.show()
