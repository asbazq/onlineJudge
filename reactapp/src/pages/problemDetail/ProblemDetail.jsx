import { useParams } from "react-router-dom"
import { Allotment } from "allotment";
import DescriptionPanel from "./DescriptionPanel";
import CodeEditor from "./CodeEditor";
import "allotment/dist/style.css";
import "./ProblemDetail.css";
import { useEffect, useState } from "react";
import { getQuestion } from "../../apiInterface/ProblemAPIs";


export default function ProblemDetail(props) {
    const [description, setDescription] = useState();
    const param = useParams();

    useEffect(() => {
        init();
    }, []);

    const init = async () => {
        const res = await getQuestion(param?.id);
        if (res.status == 200) {
            setDescription(res.data);
        }
    }

    return (
        <div className="problem-detail dark-bg">
            <Allotment proportionalLayout={false}>
                <Allotment.Pane
                    priority='low'
                    preferredSize="300px"
                >
                    <DescriptionPanel data={description} />
                </Allotment.Pane>
                <Allotment.Pane priority='high'>
                    <CodeEditor />
                </Allotment.Pane>
            </Allotment>
        </div>
    )
}