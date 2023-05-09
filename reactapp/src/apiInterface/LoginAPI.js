import { makeAxiosGetRequest, makeAxiosPostRequest } from "./Common"

const base_url = 'api'
export async function signUpAPI() {
    const url = `${base_url}/join`;
    const res = await makeAxiosGetRequest(url, param);
    return res;
}
