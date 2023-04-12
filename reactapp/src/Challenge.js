import React, { useState } from "react";
import './Challenge.css';


const Challenge = () => {
    const [output, setOutput] = useState("");
    const [code, setCode] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [selectLanguage, setSelectLanguage] = useState("js");

    const handleLanguageChange = (e) => {
        setSelectLanguage(e.target.value);
    }


    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);

        // Your code to submit the challenge and get the output goes here
        // ...

        setOutput("Your output goes here");
        setIsLoading(false);
    };

    return (
        <div className="challenge-container">
            <div className="challenge-header">
                <h4>Main.{selectLanguage}</h4>
                <label htmlFor="language-select"></label>
                <select id="language-select" value={selectLanguage} onChange={handleLanguageChange}>
                    <option value="js">JavaSvript</option>
                    <option value="py">Python</option>
                    <option value="java">Java</option>
                    <option value="c">C</option>
                    <option value="cpp">C++</option>
                </select>
            </div>

            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="code">Code:</label>
                    <textarea
                        id="code"
                        className="form-control"
                        rows="10"
                        value={code}
                        onChange={(e) => setCode(e.target.value)}
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="output">Output:</label>
                    <textarea
                        id="output"
                        className="form-control"
                        rows="5"
                        value={output}
                        readOnly
                    />
                </div>

                <button type="submit" className="btn btn-primary" disabled={isLoading}>
                    {isLoading ? "Loading..." : "Submit"}
                </button>
            </form>
        </div>
    );
};

export default Challenge;
