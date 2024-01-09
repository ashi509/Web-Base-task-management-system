package WebBased.Task.Management.System.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class GenericException extends Throwable {
    private int statusCode;
    private String errorMessage;

    public GenericException(int statusCode, String errorMessage) {
        super(errorMessage);
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }
}
