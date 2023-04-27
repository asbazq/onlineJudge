import React, { useState } from "react";
import CodeMirror from '@uiw/react-codemirror';
import './CodeEditor.css';
import { Allotment } from "allotment";
import { MenuItem, Select, FormControl } from "@mui/material";


export default function CodeEditor(props) {
    const {
        onChange: onChangeCallback = () => { },
        consoleText,
    } = props;

    const onChange = (e) => {
        onChangeCallback(e);
    }

    return (
        <div className="code-editor">
            <div className="toolbar">
                <FormControl sx={{ minWidth: 120 }}>
                    <Select defaultValue={"java"}>
                        <MenuItem value="py">Python</MenuItem>
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
                        onChange={onChange}
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
                <button className="confirm-button">submit</button>
            </div>
        </div>
    );
};

