package InterviewQuestions;

/**
 * Queue with two stacks.
 * Implement a queue with two stacks so that each queue operations takes a constant amortized number of stack operations.
 */

public class InterviewQ1 {
/**
 * One stack is used to hold the queue, the other is used as a temporary inverter to hold the first stack when
 * adding a new element.
 *
 * When performing a queue operation, we pop each element from the first stack and push it onto the second stack, then
 * we push the new element onto the first stack, and finally pop each element from the second stack and push it onto
 * the first stack.
 *
 * When performing a dequeue operation we only need to pop an element from the first stack.
 */

}
