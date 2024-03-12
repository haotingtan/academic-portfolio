//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project P08: DNA Transcription - DNA class
// Course:   CS 300 Spring 2022
//
// Author:   Haoting Tan
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// NONE
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         NONE
// Online Sources:  NONE
//
///////////////////////////////////////////////////////////////////////////////

/**
 * This class uses a linked queue to implement DNA transcription.
 */
public class DNA {
  // A two-dimensional array containing the mRNA codons in index 0 and the corresponding
  // amino acid (or STOP) in index 1
  protected static String[][] mRNAtoProteinMap =
      {{"UUU", "F"}, {"UUC", "F"}, {"UUA", "L"}, {"UUG", "L"}, {"UCU", "S"}, {"UCC", "S"},
          {"UCA", "S"}, {"UCG", "S"}, {"UAU", "Y"}, {"UAC", "Y"}, {"UAA", "STOP"}, {"UAG", "STOP"},
          {"UGU", "C"}, {"UGC", "C"}, {"UGA", "STOP"}, {"UGG", "W"}, {"CUU", "L"}, {"CUC", "L"},
          {"CUA", "L"}, {"CUG", "L"}, {"CCU", "P"}, {"CCC", "P"}, {"CCA", "P"}, {"CCG", "P"},
          {"CAU", "H"}, {"CAC", "H"}, {"CAA", "Q"}, {"CAG", "Q"}, {"CGU", "R"}, {"CGC", "R"},
          {"CGA", "R"}, {"CGG", "R"}, {"AUU", "I"}, {"AUC", "I"}, {"AUA", "I"}, {"AUG", "M"},
          {"ACU", "T"}, {"ACC", "T"}, {"ACA", "T"}, {"ACG", "T"}, {"AAU", "N"}, {"AAC", "N"},
          {"AAA", "K"}, {"AAG", "K"}, {"AGU", "S"}, {"AGC", "S"}, {"AGA", "R"}, {"AGG", "R"},
          {"GUU", "V"}, {"GUC", "V"}, {"GUA", "V"}, {"GUG", "V"}, {"GCU", "A"}, {"GCC", "A"},
          {"GCA", "A"}, {"GCG", "A"}, {"GAU", "D"}, {"GAC", "D"}, {"GAA", "E"}, {"GAG", "E"},
          {"GGU", "G"}, {"GGC", "G"}, {"GGA", "G"}, {"GGG", "G"}};

  // The queue containing the original DNA sequence
  protected LinkedQueue<Character> DNA;

  /**
   * Creates the DNA queue from the provided String.
   * Each Node contains a single Character from the sequence.
   *
   * @param sequence a String containing the original DNA sequence
   */
  public DNA(String sequence) {
    DNA = new LinkedQueue<>();
    if (sequence.isBlank()) {
      DNA.front = null;
      DNA.back = null;
      return;
    } else {
      sequence = sequence.replaceAll(" ", "");
      for (int i = 0; i < sequence.trim().length(); i++) {
        DNA.enqueue(sequence.charAt(i));
      }
    }
  }

  /**
   * Creates and returns a new queue of amino acids from a provided queue
   * of mRNA characters. The translation is done three characters at a time,
   * according to the static mRNAtoProteinMap provided above. Translation ends
   * either when you run out of mRNA characters OR when a STOP codon is reached
   * (i.e. the three-character sequence corresponds to the word STOP in the map,
   * rather than a single amino acid character).
   *
   * @param mRNA a queue containing the mRNA sequence corresponding to the stored DNA sequence
   * @return the queue containing the amino acid sequence corresponding
   * to the provided mRNA sequence
   */
  public LinkedQueue<String> mRNATranslate(LinkedQueue<Character> mRNA) {
    LinkedQueue<String> aminoAcids = new LinkedQueue<String>();
    if (mRNA == null || mRNA.size() == 0 ||
        mRNA.front == null || mRNA.back == null) {
      aminoAcids.front = null;
      aminoAcids.back = null;
      return aminoAcids;
    }

    String mRNAString = mRNA.toString().replaceAll(" ", "");
    boolean needStop = false;
    for (int i=0; i<mRNAString.length()-mRNAString.length()%3; i=i+3) {
      String oneAminoAcid = mRNAString.substring(i, i+3);
      for (int j=0; j<mRNAtoProteinMap.length; j++) {
        if (oneAminoAcid.equals(mRNAtoProteinMap[j][0])) {
          if (mRNAtoProteinMap[j][1].trim().equals("STOP")) {
            needStop = true;
          } else {
            aminoAcids.enqueue(mRNAtoProteinMap[j][1]);
          }
          break;
        }
        if (needStop) {
          break;
        }
      }
    }

    return aminoAcids;
  }

  /**
   * Creates and returns a new queue of mRNA characters from the stored DNA.
   * The transcription is done one character at a time, as (A->U, T->A, C->G, G->C).
   *
   * @return the queue containing the mRNA sequence corresponding to the stored DNA sequence
   */
  public LinkedQueue<Character> transcribeDNA() {
    LinkedQueue<Character> mRNA = new LinkedQueue<>();
    if (this.DNA == null || this.DNA.size() == 0 ||
        this.DNA.front == null || this.DNA.back == null) {
      mRNA.front = null;
      mRNA.back = null;
      return mRNA;
    }
    int DNASize = this.DNA.size();
    for (int i=0; i<DNASize; i++) {
      Character DNAPart = this.DNA.dequeue();
      Character RNAPart;
      if (DNAPart.equals('A')) {
        RNAPart = 'U';
      } else if (DNAPart.equals('T')) {
        RNAPart = 'A';
      } else if (DNAPart.equals('G')) {
        RNAPart = 'C';
      } else {
        RNAPart = 'G';
      }
      mRNA.enqueue(RNAPart);
      DNA.enqueue(DNAPart);
    }
    return mRNA;
  }

  /**
   * A shortcut method that translates the stored DNA sequence to a queue
   * of amino acids using the other two methods in this class
   *
   * @return the queue containing the amino acid sequence corresponding
   * to the stored DNA sequence, via its mRNA transcription
   */
  public LinkedQueue<String> translateDNA() {
    LinkedQueue<Character> mRNA = transcribeDNA();
    LinkedQueue<String> aminoAcids = mRNATranslate(mRNA);
    return aminoAcids;
  }
}
