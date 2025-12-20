// Grouped custom exceptions for simplicity
class DuplicateIdException extends Exception {
    public DuplicateIdException(String message) { super(message); }
}

class ItemUnavailableException extends Exception {
    public ItemUnavailableException(String message) { super(message); }
}

class ValidationException extends Exception {
    public ValidationException(String message) { super(message); }
}