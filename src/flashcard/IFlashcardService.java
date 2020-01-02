package flashcard;
import java.util.*;

public interface IFlashcardService {
	public void AddFlashcard(Flashcard flashcard);
	public void RemoveFlashcard(Flashcard flashcard);
	public List<Flashcard> GetFlashcards();
}
