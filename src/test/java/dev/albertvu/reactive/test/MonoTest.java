package dev.albertvu.reactive.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class MonoTest {
	@Test
	public void monoSubscriber() {
		String name = "Albert Vu";
		Mono<String> mono = Mono.just(name)
				.log();

		mono.subscribe();
		log.info("----------------------------------------------------");

		StepVerifier.create(mono)
				.expectNext(name)
				.verifyComplete();
	}

	@Test
	public void monoSubscriberConsumer() {
		String name = "Albert Vu";
		Mono<String> mono = Mono.just(name)
				.log();

		mono.subscribe(s -> log.info("value {}", s));
		log.info("----------------------------------------------------");

		StepVerifier.create(mono)
				.expectNext(name)
				.verifyComplete();
	}

	@Test
	public void monoSubscriberConsumerError() {
		String name = "Albert Vu";
		Mono<String> mono = Mono.just(name)
				.map(s -> {throw new RuntimeException("Testing mono with error");});

		mono.subscribe(s -> log.info("Name {}", s), s -> log.error("something bad happened"));
		mono.subscribe(s -> log.info("Name {}", s), Throwable::printStackTrace);
		log.info("----------------------------------------------------");

		StepVerifier.create(mono)
				.expectError(RuntimeException.class)
				.verify();
	}
}
