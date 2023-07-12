package com.vitu.todoListchallenge;

import com.vitu.todoListchallenge.entity.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//-> sobe a aplicação em uma porta aleatória
class TodoListChallengeApplicationTests {
	@Autowired
	private WebTestClient webTestClient; //-> Versão asyncrona do RestTemplate

	@Test
	void testCreateTodoSucess() {
		var todo = new Todo("todo1", "description1", false, 1);

		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(todo)
				.exchange()//-> executa a requisição
				.expectStatus().isOk()//-> espera que o status seja 200
				.expectBody()//-> espera que o corpo da resposta seja um json
				.jsonPath("$").isArray() //-> $ é a lista de todos, isArray() verifica se é um array
				.jsonPath("$.length()").isEqualTo(1) //-> $.length chegar o tamanho da lista de todos, e verificamos se é igual a 1
				.jsonPath("$[0].name").isEqualTo(todo.getName()) //-> $[0] é o primeiro elemento da lista, e verificamos se o nome é igual a todo.getName()
				.jsonPath("$[0].description").isEqualTo(todo.getDescription())
				.jsonPath("$[0].done").isEqualTo(todo.isDone())
				.jsonPath("$[0].priority").isEqualTo(todo.getPriority());
	}
	@Test
	void testCreateTodoFailure() {
		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(new Todo("", "", false, 0)) // -> colocando valores para forçar o erro
				.exchange()
				.expectStatus().isBadRequest(); //-> espera que o status seja 400
	}

}
