package flashcard;

import java.util.UUID;

public class Flashcard {
	
	Flashcard(String front, String back)
	{
		Front = front;
		Back = back;
		Id = UUID.randomUUID();
	}
	
	Flashcard(String front, String back, UUID id)
	{
		Front = front;
		Back = back;
		Id = id;		
	}
	
	private UUID Id;
	private String Front;
	private String Back;

	public String GetFront()
	{
		return Front;
	}
	
	public void EditFront(String newFront)
	{
		Front = newFront;
	}
	
	public String GetBack()
	{
		return Back;
	}
	
	public void EditBack(String newBack)
	{
		Back = newBack;
	}
	
	public UUID GetId()
	{
		return Id;
	}
}
