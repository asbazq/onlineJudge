import { FormControl, Input, InputLabel, TextField } from "@mui/material";
import "./Login.css";
import { useRef } from "react";

export default function Login(props) {
    const loginInfo = useRef({ id: "", password: "" });

    return (
        <div>
            <form className="login-form">
                <h3 className="login-title">Sign in</h3>
                <label>
                    ID
                    <input type="text" placeholder="id" />
                </label>
                <label>
                    Password
                    <input type="password" placeholder="password" />
                </label>
            </form>
        </div>
    )
}