package flashcard;

import java.util.*;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class FlashcardService implements IFlashcardService {
	
	private List<Flashcard> _flashcards;
	
	public FlashcardService()
	{
		_flashcards = new ArrayList<Flashcard>();
		LoadFlashcards();
	}

	public void AddFlashcard(Flashcard flashcard)
	{
		_flashcards.add(flashcard);
		UpdateJson();
	}

	public void RemoveFlashcard(Flashcard flashcard)
	{
		int index = _flashcards.indexOf(flashcard);
		_flashcards.remove(index);
		UpdateJson();
	}
	
	public List<Flashcard> GetFlashcards()
	{
		List<Flashcard> flashcards = new ArrayList<Flashcard>(_flashcards);
		return flashcards;
	}
	
	private void UpdateJson() {
		JsonObject obj = new JsonObject();
		JsonArray array = new JsonArray();
		for (Flashcard card : _flashcards)
		{
			JsonObject flashcard = new JsonObject();
			flashcard.put("Id", card.GetId().toString());
			flashcard.put("Back", card.GetBack());
			flashcard.put("Front", card.GetFront());
			array.add(flashcard);
		}
		
		obj.put("Flashcards", array);
		
		try {
			File file = new File("flashcards.json");
			file.createNewFile();
		    FileWriter writer = new FileWriter(file); 
		    Jsoner.serialize(obj, writer);
		    writer.flush();
		    writer.close();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	private void LoadFlashcards() {
		File flashcardsFile = new File("flashcards.json");
		if(!flashcardsFile.exists())
		{
			UpdateJson();
		}

	    try (Reader reader = new FileReader("flashcards.json")) {

	    	JsonObject jsonObject = (JsonObject) Jsoner.deserialize(reader);

	        JsonArray flashcards = (JsonArray) jsonObject.get("Flashcards");
	        Iterator<Object> it = flashcards.iterator();
	        while (it.hasNext()) {
	        	JsonObject readCard = (JsonObject) it.next();
	          	Flashcard card = new Flashcard(
	         			readCard.get("Front").toString(),
	         			readCard.get("Back").toString(),
	          			UUID.fromString(readCard.get("Id").toString()));
	          	_flashcards.add(card);
	           }    
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (JsonException e) {
				e.printStackTrace();
			}
	}
}
