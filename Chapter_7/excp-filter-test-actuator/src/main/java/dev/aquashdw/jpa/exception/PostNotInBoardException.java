package dev.aquashdw.jpa.exception;

public class PostNotInBoardException extends BaseException{
    public PostNotInBoardException() {
        super("Post not in board");
    }
}
