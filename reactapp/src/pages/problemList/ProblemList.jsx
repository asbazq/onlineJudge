import { useEffect, useState } from "react"
import { getAllProblems } from "../../apiInterface/ProblemAPIs";


const dummies = [1, 2, 3, 4, 5]
export default function ProblemList(props) {
    const [problems, setProblems] = useState([]);

    useEffect(() => {
        init();
    }, []);

    const init = async () => {
        const res = await getAllProblems();
        if (res.status === 200) {
            setProblems(res.data);
        }
    }

    return (
        <div className="App">
            <header className="header">
                <h1>Coding Test Site</h1>
                <nav className="navbar">
                    <a href="">Home</a>
                    <a href="#">Challenges</a>
                </nav>
            </header>
            <main className="content">
                <h2>Coding Test Site!</h2>
                <ul className="challenge-list">
                    {
                        problems?.map((problem, i) => {
                            return (
                                <li key={i}>
                                    <a href={`/problems/${problem.questionId}`} >{problem.title}</a>
                                </li>
                            )
                        })
                    }
                </ul>
            </main>
            <footer className="footer">
                <p>© 2023 Coding Test Site.</p>
                <p>모든 콘텐츠, 정보, UI, HTML 소스 등에 대한 무단 복제, 전송, 배포, 크롤링, 스크래핑 등의 행위를 거부하며, 이러한 행위는 관련 법령에 의해 엄격히 금지됩니다.</p>
            </footer>
        </div>
    )
}