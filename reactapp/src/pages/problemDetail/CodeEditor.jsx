import React, { useState } from "react";
import CodeMirror from '@uiw/react-codemirror';
import './CodeEditor.css';
import SelectBox from "../../common/SelectBox";
import { Allotment } from "allotment";
import ResultPanel from "./ResultPanel";


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
                <SelectBox
                    options={[1, 2, 3, 4, 5]}
                />
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

