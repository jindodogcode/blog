export class DisplayError {
  /**
   * DisplayError constructor
   *
   * @param {String} message
   */
  constructor(message) {
    this.message = message;
  }
}

// class SilentError {}

export class UnauthorizedError extends DisplayError {
  /**
   * UnauthorizedLoginError constructor
   */
  constructor() {
    super("Unauthorized");
  }
}

export class ValidationError extends DisplayError {
  /**
   * ValidationError constructor
   *
   * @param {Array} subErrors
   */
  constructor(subErrors) {
    let message = "Validation errors:\n\n";
    for (const error of subErrors) {
      message += error.message + "\n";
    }
    super(message);
  }
}

export class UserNotFoundError extends DisplayError {
  /**
   * UserNotFoundError constructor
   */
  constructor() {
    super("User not found");
  }
}

export class ConflictError extends DisplayError {
  /**
   * ConflictError constructor
   */
  constructor() {
    super("username or email address already registered");
  }
}
