//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Project P08: DNA Transcription - DNATester class
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
import java.util.NoSuchElementException;

/**
 * Test methods to verify your implementation of the methods for P08.
 */
public class DNATester {

  /**
   * Tests the Node class' constructor and methods
   *
   * @return true if and only if the Node class' constructor and methods works correctly
   */
  public static boolean testNode() {
    try {
      // 1. test if the next node is null when passing only the data
      {
        Node<Integer> testNode = new Node(new Integer(10));
        if (testNode.getNext() != null) {
          System.out.println("Problem detected: Node class' constructor or getNext() "
              + "method encounters problem: get unexpected result(case1.1)");
          return false;
        }
      }

      // 2. test the constructor correctly set the next node and correctness of getData()
      {
        Node<Integer> secondNode = new Node(20);
        Node<Integer> testNode = new Node(new Integer(10), secondNode);
        if (testNode.getNext() != secondNode) {
          System.out.println("Problem detected: Node class' constructor or "
              + "getNext() method encounters problem: get unexpected result(case:2.1)");
          return false;
        }
        if (testNode.getData() != 10 || testNode.getNext().getData() != 20) {
          System.out.println("Problem detected: Node class' constructor or "
              + "getNext() method encounters problem: get unexpected result(case:2.2)");
          return false;
        }
      }

      // 3. test setNext()
      {
        Node<String> testNode = new Node("first");
        Node<String> testSecondNode = new Node("second");
        testNode.setNext(testSecondNode);
        if (testNode.getNext() != testSecondNode) {
          System.out.println("Problem detected: Node class' setNext() method encounters "
              + "problem: get unexpected result(case:3.1)");
          return false;
        }
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your Node class encounters unexpected "
          + "exception in enqueue() method : " + e.getClass());
      return false;
    }

    return true; // no BUGs detected in Node class.
  }

  /**
   * Tests the enqueue() and dequeue() method in LinkedQueue class
   *
   * @return true if and only if the methods work correctly
   */
  public static boolean testEnqueueDequeue() {
    // 1. test if dequeue() throws NoSuchElementException if queue is Empty
    {
      try {
        LinkedQueue<Integer> testQueue = new LinkedQueue<>();
        testQueue.dequeue();
        System.out.println("Problem detected: Your LinkedQueue class' "
            + "dequeue does NOT throw the exception when the queue is empty");
        return false;
      } catch (NoSuchElementException e1) {
        // true
      } catch (Exception e2) {
        System.out.println("Problem detected: Your LinkedQueue class' dequeue "
            + "throws the Wrong exception when the queue is empty");
        return false;
      }
    }

    // 2. test if enqueue() works functionally
    {
      try {
        LinkedQueue<Integer> testQueue = new LinkedQueue<>();
        testQueue.enqueue(10);
        testQueue.enqueue(20);
        testQueue.enqueue(30);
        if (testQueue.front.getData() != 10 || testQueue.back.getData() != 30
            || testQueue.size() != 3) {
          System.out.println("Problem detected: Your enqueue() method returns the "
              + "unexpected result(case:2.1)");
          return false;
        }
        if (testQueue.front.getNext().getData() != 20 ||
            testQueue.front.getNext().getNext().getData() != 30) {
          System.out.println("Problem detected: Your enqueue() method returns the "
              + "unexpected result(case:2.2)");
          return false;
        }
        String testQueueString = testQueue.toString().replaceAll(" ", "");
        if (!testQueueString.equals("102030")) {
          System.out.println("Problem detected: Your enqueue() method returns the "
              + "unexpected result or toString() method implements is incorrect (case:2.3)");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your LinkedQueue class encounters unexpected "
            + "exception in enqueue() method : " + e.getClass());
        return false;
      }
    }

    // 3. test if dequeue() works functionally
    {
      try {
        LinkedQueue<Integer> testQueue = new LinkedQueue<>();
        testQueue.enqueue(10);
        testQueue.enqueue(20);
        testQueue.enqueue(30);

        Integer remove1 = testQueue.dequeue();
        if (remove1 != 10 || testQueue.front.getData() != 20 || testQueue.back.getData() != 30
            || testQueue.size() != 2
            || !testQueue.toString().replaceAll(" ", "").equals("2030")) {
          System.out.println("Problem detected: Your dequeue() method returns the "
              + "unexpected result(case:3.1)");
          return false;
        }
        Integer remove2 = testQueue.dequeue();
        if (remove2 != 20 || testQueue.front.getData() != 30 || testQueue.back.getData() != 30
            || testQueue.size() != 1 || !testQueue.toString().trim().equals("30")) {
          System.out.println("Problem detected: Your dequeue() method returns the "
              + "unexpected result(case:3.2)");
          return false;
        }
        Integer remove3 = testQueue.dequeue();
        if (remove3 != 30 || testQueue.front != null || testQueue.back != null
            || testQueue.size() != 0 || !testQueue.toString().equals("")) {
          System.out.println("Problem detected: Your dequeue() method returns the "
              + "unexpected result(case:3.3)");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your LinkedQueue class encounters unexpected "
            + "exception in enqueue() method : " + e.getClass());
        return false;
      }
    }

    return true; // no BUGs detected in enqueue() and dequeue methods
  }

  /**
   * Tests the LinkedQueue class' queue's size and isEmpty() method
   *
   * @return true if and only if the method works correctly
   */
  public static boolean testQueueSize() {
    try {
      LinkedQueue<String> testQueue = new LinkedQueue<>();
      if (testQueue.size() != 0 || !testQueue.isEmpty()) {
        System.out.println("Problem detected: Your isEmpty() method returns the "
            + "unexpected result or LinkedQueue has wrong size (case:1)");
        return false;
      }
      testQueue.enqueue("10");
      testQueue.enqueue("20");
      testQueue.enqueue("30");
      if (testQueue.size() != 3 || testQueue.isEmpty()) {
        System.out.println("Problem detected: Your isEmpty() method returns the "
            + "unexpected result or LinkedQueue has wrong size (case:2)");
        return false;
      }
      testQueue.peek();
      if (testQueue.size() != 3 || testQueue.isEmpty()) {
        System.out.println("Problem detected: Your isEmpty() method returns the "
            + "unexpected result or LinkedQueue has wrong size (case:3)");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your LinkedQueue class encounters unexpected "
          + "exception in isEmpty() method : " + e.getClass());
      return false;
    }

    return true; // No bugs detected in LinkedQueue class' size and isEmpty() method
  }

  /**
   * Tests the transcribeDNA() method
   * @return true if and only if the method works correctly
   */
  public static boolean testTranscribeDNA() {
    DNA testDNA = new DNA("GGAGTCAGTTAAGCGACCGGGACATACTGTCTTGGTAATCTCCGAGCTAGAAAGTCTCTG");
    String mRNA = "CCUCAGUCAAUUCGCUGGCCCUGUAUGACAGAACCAUUAGAGGCUCGAUCUUUCAGAGAC";
    System.out.println(testDNA.transcribeDNA().toString());
    if (testDNA.transcribeDNA().toString().replaceAll(" ", "").equals(mRNA)) {
      return true;
    }
    return false;
  }

  /**
   * Tests the translateDNA() method
   * @return true if and only if the method works correctly
   */
  public static boolean testTranslateDNA() {
    try {
      DNA testDNA = new DNA("GGAGTCAGTTAAGCGACCGGGACATACTGTCTTGGTAATCTCCGAGCTAGAAAGTCTCTG");
      System.out.println(testDNA.translateDNA().toString());
      String expected = "PQSIRWPCMTEPLEARSFRD";
      if (!testDNA.translateDNA().toString().replaceAll(" ", "").equals(expected)) {
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your DNA class encounters unexpected "
          + "exception in translateDNA() method : " + e.getClass());
      return false;
    }

    try {
      DNA testDNA = new DNA("GGA");
      System.out.println(testDNA.translateDNA().toString());
      String expected = "P";
      if (!testDNA.translateDNA().toString().replaceAll(" ", "").equals(expected)) {
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your DNA class encounters unexpected "
          + "exception in translateDNA() method : " + e.getClass());
      return false;
    }

    return true; // no BUGs detected in translateDNA
  }

  /**
   * Tests the translateDNA() method
   * @return true if and only if the method works correctly
   */
  public static boolean testMRNATranslate() {
    // 1. test if mRNA(input) LinkedQueue size is less than 3
    {
      try {
        DNA testDNA = new DNA("GG");
        LinkedQueue<Character> mRNA = testDNA.transcribeDNA();
        LinkedQueue<String> testAminoAcids = testDNA.mRNATranslate(mRNA);
        if (!testAminoAcids.toString().equals("")) {
          System.out.println("Problem detected: Your mRNATranslate return"
              + " the unexpected result(case:1)");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your DNA class encounters unexpected "
            + "exception in mRNATranslate() method : " + e.getClass());
        return false;
      }
    }

    // 2. test if mRNA Linked queue size is 3 non "STOP"
    {
      try {
        DNA testDNA = new DNA("GGA");
        LinkedQueue<Character> mRNA = testDNA.transcribeDNA();
        LinkedQueue<String> testAminoAcids = testDNA.mRNATranslate(mRNA);
        if (!testAminoAcids.toString().trim().equals("P")) {
          System.out.println("Problem detected: Your mRNATranslate return"
              + " the unexpected result(case:2)");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your DNA class encounters unexpected "
            + "exception in mRNATranslate() method : " + e.getClass());
        return false;
      }
    }

    // 3. test if mRNA Linked queue size is 4 (has reminder by divided by 3)
    {
      try {
        DNA testDNA = new DNA("GGAG");
        LinkedQueue<Character> mRNA = testDNA.transcribeDNA();
        LinkedQueue<String> testAminoAcids = testDNA.mRNATranslate(mRNA);
        if (!testAminoAcids.toString().replaceAll(" ", "").equals("P")) {
          System.out.println("Problem detected: Your mRNATranslate return"
              + " the unexpected result(case:3)");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your DNA class encounters unexpected "
            + "exception in mRNATranslate() method : " + e.getClass());
        return false;
      }
    }

    // 4. test if mRNA contains "STOP" amino acids
    {
      try {
        DNA testDNA = new DNA("GGAGTCACTATCG");
        LinkedQueue<Character> mRNA = testDNA.transcribeDNA();
        LinkedQueue<String> testAminoAcids = testDNA.mRNATranslate(mRNA);
        if (!testAminoAcids.toString().replaceAll(" ", "").equals("PQ")) {
          System.out.println("Problem detected: Your mRNATranslate return"
              + " the unexpected result(case:4)");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your DNA class encounters unexpected "
            + "exception in mRNATranslate() method : " + e.getClass());
        return false;
      }
    }

    // 5. test if mRNA Linked queue size is 3 and means "STOP"
    {
      try {
        DNA testDNA = new DNA("ATT");
        LinkedQueue<Character> mRNA = testDNA.transcribeDNA();
        LinkedQueue<String> testAminoAcids = testDNA.mRNATranslate(mRNA);
        if (!testAminoAcids.toString().replaceAll(" ", "").equals("")) {
          System.out.println("Problem detected: Your mRNATranslate return"
              + " the unexpected result(case:5)");
          return false;
        }
      } catch (Exception e) {
        System.out.println("Problem detected: Your DNA class encounters unexpected "
            + "exception in mRNATranslate() method : " + e.getClass());
        return false;
      }
    }

    return true; // no BUGs detected in mRNATranslate() method
  }

  /**
   * Main method - use this to run your test methods and output the results (ungraded)
   * @param args unused
   */
  public static void main(String[] args) {
    System.out.println("testNode: "+testNode());
    System.out.println("testEnqueueDequeue: "+testEnqueueDequeue());
    System.out.println("testQueueSize: "+testQueueSize());
    System.out.println("transcribeDNA: "+testTranscribeDNA());
    System.out.println("translateDNA: "+testTranslateDNA());
    System.out.println("testMRNATranslate: "+testMRNATranslate());
  }
}
