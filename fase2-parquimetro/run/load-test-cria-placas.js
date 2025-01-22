import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    stages: [
        { duration: '1s', target: 10 },
        { duration: '10s', target: 10 }
    ],
};


// The default exported function is gonna be picked up by k6 as the entry point for the test script.
// It will be executed repeatedly in "iterations" for the whole duration of the test.
export default function () {

    const endpoint = 'http://localhost:8080/parquimetro/v2/bilhete';
    const payload = JSON.stringify({
        placa: gerarPlacaAleatoria(),
        tempoEmHoras: 1,
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };
    http.post(endpoint, payload, params);

    // Sleep for 1 second to simulate real-world usage
    sleep(1);
}

function gerarPlacaAleatoria() {
    const letras = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const numeros = '0123456789';

    return `${gerarLetras(letras, 3)}${gerarNumeros(numeros, 1)}${gerarLetras(letras, 1)}${gerarNumeros(numeros, 2)}`;
}

// Função para gerar letras aleatórias
function gerarLetras(alfabeto, quantidade) {
    return Array.from({ length: quantidade }, () =>
        alfabeto.charAt(Math.floor(Math.random() * alfabeto.length))
    ).join('');
}

// Função para gerar números aleatórios
function gerarNumeros(digitos, quantidade) {
    return Array.from({ length: quantidade }, () =>
        digitos.charAt(Math.floor(Math.random() * digitos.length))
    ).join('');
}
