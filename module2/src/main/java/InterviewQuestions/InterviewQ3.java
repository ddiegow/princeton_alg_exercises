package InterviewQuestions;

/**
 * Java generics.
 * Explain why Java prohibits generic array creation.
 */
public class InterviewQ3 {
    /**
     * The reason is that arrays are covariant, which means that a subclass type array can be assigned to its superclass
     * type array. Therefore, were generic array creation allowed, we would be able to create an array of a generic
     * type A, assign it to an array of objects, assign one of its elements to a subclass array of a type B different
     * from the first one, try to assign said element to a variable of type A.  We would get no compilation time errors,
     * but we would get a run-time error, as we are trying to assign an  element of type B to a variable of type A.
     */
}
