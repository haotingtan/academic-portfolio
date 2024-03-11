# p1: Fortune
The Caesar cipher is a very simple way of encoding messages. The idea is that each character (that is a lowercase letter) in the original (or plaintext) message is shifted forward or backward by some number to get the encoded message(or ciphertext). So for instance, given the plaintext
attack at dawn!
a forward shift of 3 for every character would result in the ciphertext
dwwdfn dw gdzq!
To decode this message we simply shift back every character by 3.
For this assignment you will compile and run a program that has been provided for you to decode a mystery message that was encoded specifically for you. The original plaintext is a fortune cookie message which has been encoded by a backward shift of all characters by some number. The exact number of shifts to encode your fortune cookie is determined by some computation on your CS login username.
