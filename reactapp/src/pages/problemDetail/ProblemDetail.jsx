import { useParams } from "react-router-dom"

export default function ProblemDetail(props) {
    const param = useParams();

    console.log(param);
    return (
        <div>
            problem detail
            <div>description</div>
            <div>code editor</div>
        </div>
    )
}