package InterviewQuestions;

/**
 * Stack with max.
 * Create a data structure that efficiently supports the stack operations (push and pop) and also a return-the-maximum
 * operation. Assume the elements are real numbers so that you can compare them.
 */

public class InterviewQ2 {
    /**
     * I would use a stack with two linked lists.  One would hold the normal stack and the other would hold a list
     * of pointers to the historical maximum values.
     * When adding a new element, if it's greater than the latest maximum, add a new pointer to the historical list of
     * maximum values.
     * When fetching the maximum value, remove the maximum element from the stack list that the pointer on the
     * historical list of maximum values points to and remove said pointer from the list
     */
}
