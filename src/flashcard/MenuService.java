package flashcard;
import java.util.*;


public class MenuService implements IMenuService {
	
	private Scanner _scanner;
	private FlashcardService _fcService;
	
	MenuService()
	{
		_scanner = new Scanner(System.in);
		_fcService = new FlashcardService();
	}	
	
	public void GetMainMenu()
	{
		while(true)
		{
			System.out.println("Flashcard - Menu g³ówne");
			System.out.println("E - Edycja i tworzenie fiszek");
			System.out.println("N - Nauka");
			System.out.println("X - Wyjœcie");
			
			String choice = _scanner.nextLine();
			
			switch(choice)
			{
				case "e":
				case "E":
					GetEditMenu();
					break;
				case "n":
				case "N":
					GetLearningMenu();
					break;
				case "x":
				case "X":
					System.exit(1);
					break;
			}
		}
	}	
	
	private void GetEditMenu() {
		while(true)
		{
			System.out.println("Flashcard - Menu edycji");
			List<Flashcard> flashcards = _fcService.GetFlashcards();
			
			for (Flashcard card : flashcards)
			{
				System.out.println(flashcards.indexOf(card) + ": " + card.GetFront() + " - " + card.GetBack());
			}
			
			System.out.println("U - Usuwanie");
			System.out.println("D - Dodawanie fiszek");
			System.out.println("P - Powrót");
			System.out.println("X - Wyjœcie");
			
			String choice = _scanner.nextLine();
			
			switch(choice)
			{
				case "u":
				case "U":
					GetRemoveFlashcardMenu();
					GetEditMenu();
					break;
				case "d":
				case "D":
					GetAddFlashcardMenu();
					GetEditMenu();
					break;
				case "P":
				case "p":
					GetMainMenu();
					break;
				case "x":
				case "X":
					System.exit(1);
					break;
			}
		}
	}
	

	private void GetAddFlashcardMenu() {
		String front;
		String back;
		boolean repeatMenu = true;
		
		while(repeatMenu)
		{
			System.out.println("Dodawanie fiszek [Wpisz Q by wyjœæ]");
			System.out.println("Podaj przód fiszki: ");
			front = _scanner.nextLine();
			
			if (front.equals("Q") || front.equals("q"))
			{
				repeatMenu = false;
			}
			
			if (repeatMenu)
			{
				System.out.print("Podaj ty³ fiszki: ");
				back = _scanner.nextLine();

				if (back.equals("Q") || back.equals("q"))
				{
					repeatMenu = false;
				}
				
				if (repeatMenu)
				{
					Flashcard flashcard = new Flashcard(front, back);
					_fcService.AddFlashcard(flashcard);
					
					System.out.println("Fiszka dodana");	
				}
			}
		}
	}

	private void GetRemoveFlashcardMenu() {
		boolean repeatMenu = true;
		while(repeatMenu)
		{
			System.out.print("Podaj indeks fiszki do usuniêcia (x by anulowaæ): ");
			String choice = _scanner.nextLine();
			
			try
			{
				int chosenIndex = Integer.parseInt(choice);
				List<Flashcard> flashcards = _fcService.GetFlashcards();
				if (chosenIndex >= 0 && chosenIndex < flashcards.size())
				{
					Flashcard flashcard = flashcards.get(chosenIndex);
					_fcService.RemoveFlashcard(flashcard);					
				}
			}
			catch(NumberFormatException e)
			{
				if (choice.equals("x") || choice.equals("X"))
				{
					repeatMenu = false;
				}
			}
		}
		
	}

	private void GetLearningMenu() {
		boolean repeatMenu = true;
		List<UUID> doneCards = new ArrayList<UUID>();
		List<Flashcard> flashcards = _fcService.GetFlashcards();
		Collections.shuffle(flashcards);
		
		if (flashcards.isEmpty())
		{
			System.out.println("Utwórz najpierw jakieœ fiszki!");
			repeatMenu = false;
		}
		
		String choice = "";
		
		while(repeatMenu)
		{
			Flashcard card = GetUndoneFlashcard(flashcards, doneCards);
			
			if (card != null)
			{
				System.out.println(card.GetFront());
				System.out.println("1 - poka¿ odpowiedz, Q - uwolnij mnie od nauki");
				choice = _scanner.nextLine();
				switch(choice)
				{
					case("1"):
						System.out.println(card.GetBack());
						break;
					case("q"):
					case("Q"):
						repeatMenu = false;
						break;
				}
				
				if (repeatMenu)
				{
					System.out.println("1 - Umiem, 2 - Nie Umiem, Q - Uwolnij mnie od nauki");
					choice = _scanner.nextLine();
					switch(choice)
					{
						case("1"):
							doneCards.add(card.GetId());
							break;
						case("2"):
							break;
						case("q"):
						case("Q"):
							repeatMenu = false;
							break;
					}
				}
			}
			else
			{
				break;
			}
		}
		
		System.out.println("Starczy na dziœ!");
		GetMainMenu();
	}

	private Flashcard GetUndoneFlashcard(List<Flashcard> flashcards, List<UUID> doneCards) {
		Collections.shuffle(flashcards);
		
		for (Flashcard flashcard : flashcards)
		{
			if (!doneCards.contains(flashcard.GetId()))
			{
				return flashcard;
			}
		}
		
        return null;
	}
}
	