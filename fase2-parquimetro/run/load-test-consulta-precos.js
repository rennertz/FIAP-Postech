import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    stages: [
        { duration: '1s', target: 30 },
        { duration: '10s', target: 30 }
    ],
};


// The default exported function is gonna be picked up by k6 as the entry point for the test script.
// It will be executed repeatedly in "iterations" for the whole duration of the test.
export default function () {

    const endpoint = 'http://localhost:8080/parquimetro/v2/bilhete/precos';
    http.get(endpoint);

    // Sleep for 1 second to simulate real-world usage
    sleep(1);
}