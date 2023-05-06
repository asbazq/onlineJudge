import React, { useRef, useState } from "react";
import CodeMirror from '@uiw/react-codemirror';
import './CodeEditor.css';
import { Allotment } from "allotment";
import { MenuItem, Select, FormControl } from "@mui/material";
import { submitAnswer } from "../../apiInterface/ProblemAPIs";
import { useAsyncError, useParams } from "react-router-dom";


export default function CodeEditor(props) {
    const {
        onChange: onChangeCallback = () => { },
        consoleText,
    } = props;

    const param = useParams();
    const [lang, setLang] = useState("java");
    const [code, setCode] = useState();

    const onCodeChanged = (e) => {
        setCode(e);
    }

    const onLangChanged = (e) => {
        setLang(e.target.value);
    }

    const submit = async () => {
        const param = {
            input: code,
            lang: lang,
        }
        const res = await submitAnswer(param.id, param);

        console.log(res);
    }

    return (
        <div className="code-editor">
            <div className="toolbar">
                <FormControl sx={{ minWidth: 120 }}>
                    <Select value={lang} onChange={onLangChanged}>
                        <MenuItem value="python">Python</MenuItem>
                        <MenuItem value="java">Java</MenuItem>
                        <MenuItem value="c">C</MenuItem>
                    </Select>
                </FormControl>
            </div>
            <Allotment
                proportionalLayout={false}
                vertical={true}
            >
                <Allotment.Pane priority="low">
                    <CodeMirror
                        onChange={onCodeChanged}
                        theme="dark"
                    />
                </Allotment.Pane>
                <Allotment.Pane
                    preferredSize="27%"
                    priority="high"
                >
                    <div className=" dark-con-bg console-panel">
                        {consoleText}
                    </div>
                </Allotment.Pane>
            </Allotment>
            <div className="button-container">
                <button className="confirm-button" onClick={submit}>submit</button>
            </div>
        </div>
    );
};

