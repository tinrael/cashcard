package example.cashcard;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
	private final CashCardRepository cashCardRepository;
	
	private CashCardController(CashCardRepository cashCardRepository) {
		this.cashCardRepository = cashCardRepository;
	}
	
	@GetMapping("/{requestedId}")
	private ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
		Optional<CashCard> cashCardOptional = cashCardRepository.findById(requestedId);
		
		if (cashCardOptional.isPresent()) {
			return ResponseEntity.ok(cashCardOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCard, UriComponentsBuilder uriComponentsBuilder) {
		CashCard savedCashCard = cashCardRepository.save(newCashCard);
		URI locationOfNewCashCard = uriComponentsBuilder
				.path("cashcards/{id}")
				.buildAndExpand(savedCashCard.id())
				.toUri();
		return ResponseEntity.created(locationOfNewCashCard).build();
	}
}
