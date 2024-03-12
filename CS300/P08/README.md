# P08 DNA Transcription

## Overview

The DNA Transcription assignment introduces students to the basics of bioinformatics by simulating the process of DNA transcription using a linked queue structure. The focus is on converting a sequence of DNA nucleotides (ACGT) into a complementary mRNA sequence, and then translating that mRNA into amino acids which could potentially form a protein. This assignment combines computer science techniques with biological processes, providing a simplified but engaging introduction to the field of bioinformatics.

### Learning Objectives

- **Linked Queue Implementation**: Explore the implementation and use of a basic linked queue structure for simulating DNA transcription.
- **Linked List Iteration**: Practice navigating through a linked list without relying on an Iterator, enhancing understanding of data structure manipulation.
- **Object-Oriented Programming**: Apply principles of object-oriented programming to model biological sequences and processes, reinforcing concepts of encapsulation and method development.
- **Unit Testing**: Gain additional experience in developing thorough unit tests to validate the correctness of the transcription process and the queue operations.

### Key Features

- **DNA to mRNA Transcription**: Implement the process of transcribing DNA sequences into mRNA, adhering to the biological rules of nucleotide pairing.
- **mRNA Translation**: Translate mRNA sequences into chains of amino acids, simulating a step in protein synthesis.
- **Queue Data Structure**: Utilize a linked queue to manage the sequences during transcription and translation, showcasing the queue's utility in ordered data processing.
- **Amino Acid Sequence Formation**: Group mRNA sequences into codons (groups of three nucleotides) and map them to corresponding amino acids or a stop signal, reflecting the complexity of genetic translation.

### Technical Implementation

- **Generic Node Class**: Design a generic `Node` class to represent each element in the linked queue, storing nucleotide or codon information.
- **LinkedQueue**: Implement a generic `LinkedQueue` class that manages the addition and removal of elements in FIFO (First-In-First-Out) order, simulating the transcription and translation processes.
- **DNA Class**: Develop the `DNA` class to encapsulate the transcription and translation methods, including converting DNA to mRNA and then mRNA to amino acids.
- **Testing and Validation**: Create a comprehensive `DNATester` class to thoroughly test all aspects of the DNA transcription and translation, ensuring the accuracy and reliability of the simulation.
