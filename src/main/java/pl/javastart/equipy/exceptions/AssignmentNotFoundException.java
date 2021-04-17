package pl.javastart.equipy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Brak wypożyczenia o takim ID")
public class AssignmentNotFoundException extends RuntimeException{
}
