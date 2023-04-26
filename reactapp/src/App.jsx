import React, { useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import './content/styles/style.css';
import ProblemList from './pages/problemList/ProblemList';
import ProblemDetail from './pages/problemDetail/ProblemDetail';

function App() {
    const [currentChallenge, setCurrentChallenge] = useState(null);
    const [isChallengeVisible, setIsChallengeVisible] = useState(false);

    const handleInputClick = () => {
        setIsChallengeVisible(true);
    };

    const handleChallengeClick = (event) => {
        event.preventDefault();
        const challengeId = event.target.dataset.id;
        setCurrentChallenge(challengeId);
    };

    const handleBackClick = () => {
        setCurrentChallenge(null);
        setIsChallengeVisible(false);
    };

    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<ProblemList />} />
                <Route path='/problems/:id' element={<ProblemDetail />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
