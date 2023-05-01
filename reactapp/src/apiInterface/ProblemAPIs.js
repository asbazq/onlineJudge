import { makeAxiosGetRequest, makeAxiosPostRequest } from "./Common"

const base_url = 'api'
export async function getAllProblems() {
    const url = `${base_url}/question`;
    const res = await makeAxiosGetRequest(url);
    return res;
}

export async function getQuestion(id) {
    const url = `${base_url}/question/${id}`;
    const res = await makeAxiosGetRequest(url);
    return res;
}

export async function submitAnswer(id, data) {
    const url = `${base_url}/submission/${id}`;
    const res = await makeAxiosPostRequest(url, data);
    return res;
}