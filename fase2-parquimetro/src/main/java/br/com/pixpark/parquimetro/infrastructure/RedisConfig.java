package br.com.pixpark.parquimetro.infrastructure;

import br.com.pixpark.parquimetro.domain.model.Bilhete;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    RedisTemplate<String, Bilhete> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Bilhete> template = new RedisTemplate<>();

        // Configuração do ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Criar o serializador configurado com o ObjectMapper
        Jackson2JsonRedisSerializer<Bilhete> valueSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, Bilhete.class);

        // Configurar os serializadores
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(valueSerializer);

        // Configurar a conexão
        template.setConnectionFactory(connectionFactory);

        return template;
    }
}
