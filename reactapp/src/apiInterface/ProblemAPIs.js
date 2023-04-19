import { makeAxiosGetRequest } from "./Common"

const base_url = 'api'
export async function getAllProblems() {
    const url = `${base_url}/question`;
    const res = await makeAxiosGetRequest(url);
    return res;
}