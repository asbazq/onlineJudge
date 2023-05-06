import React, { useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import './content/styles/style.css';
import ProblemList from './pages/problemList/ProblemList';
import ProblemDetail from './pages/problemDetail/ProblemDetail';
import NoPage from './pages/NoPage';
import Login from './pages/login/Login';

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<ProblemList />} />
                <Route path='/problems/:id' element={<ProblemDetail />} />
                <Route path='/login' element={<Login />} />
                <Route path='*' element={<NoPage />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
