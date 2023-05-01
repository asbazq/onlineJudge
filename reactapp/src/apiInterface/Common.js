import axios from 'axios';
// const BASE_URL = "13.209.88.31:8080";
const BASE_URL = "127.0.0.1:8080";

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

export async function makeAxiosPostRequest(url, data) {
    const path = `http://${BASE_URL}/${url}`;
    const config = {
        method: 'post',
        url: path,
        data: data
    }

    const res = await axios(config);
    return res;
}