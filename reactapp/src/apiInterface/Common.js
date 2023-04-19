import axios from 'axios';
const BASE_URL = "15.164.234.56:8080";

export async function makeAxiosGetRequest(url, param) {
    const path = `http://${BASE_URL}/${url}`;
    const config = {
        method: 'get',
        url: path,
        param: param
    };

    const res = await axios(config);
    return res;
}