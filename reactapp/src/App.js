import React, { useState } from 'react';
import Challenge from "./Challenge";
import './App.css';

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

  if (currentChallenge) {
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
          <button className="back-button" onClick={handleBackClick}>Back</button>
          <h2>문제 {currentChallenge} 배열 자르기</h2>
          <p>문제 {currentChallenge}:</p>
          <hr />
          <h3>문제설명</h3>
          <p>정수 배열 numbers와 정수 num1, num2가 매개변수로 주어질 때, numbers의 num1번 째 인덱스부터 num2번째 인덱스까지 자른 정수 배열을 return 하도록 solution 함수를 완성해보세요.</p>
          <hr />
          <h3>제한사항</h3>
          <li>
            2 ≤ numbers의 길이 ≤ 30
          </li>
          <li>
            0 ≤ numbers의 원소 ≤ 1,000
          </li>
          <li>
            0 ≤num1 &#60; num2 &#60; numbers의 길이
          </li>
          <div className="editor"> {isChallengeVisible ? (
            <Challenge />
          ) : (
            <button
              type="button"
              className="btn btn-primary"
              onClick={handleInputClick}
            >
              Challenge
            </button>
          )}
          </div>
        </main>
        <footer className="footer">
          <p>© Coding Test Site. Test.</p>
        </footer>
      </div>
    );
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
          <li><a href="#" data-id="1" onClick={handleChallengeClick}>문제 1</a></li>
          <li><a href="#" data-id="2" onClick={handleChallengeClick}>문제 2</a></li>
          <li><a href="#" data-id="3" onClick={handleChallengeClick}>문제 3</a></li>
          <li><a href="#" data-id="4" onClick={handleChallengeClick}>문제 4</a></li>
        </ul>
      </main>
      <footer className="footer">
        <p>© 2023 Coding Test Site.</p>
        <p>모든 콘텐츠, 정보, UI, HTML 소스 등에 대한 무단 복제, 전송, 배포, 크롤링, 스크래핑 등의 행위를 거부하며, 이러한 행위는 관련 법령에 의해 엄격히 금지됩니다.</p>
      </footer>
    </div>
  );
}

export default App;
