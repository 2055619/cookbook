import React from 'react';
import './App.css';
import {ToastContainer} from 'react-toastify';
import {BrowserRouter} from 'react-router-dom';
import {I18nextProvider} from "react-i18next";
import i18n from "./assets/utils/i18n";
import axios from "axios";
import Main from "./pages/Main";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  return (
      <I18nextProvider i18n={i18n}>
        <ToastContainer
            position="top-left"
            autoClose={3000}
            hideProgressBar={false}
            newestOnTop={true}
            closeOnClick
            rtl={false}
            pauseOnFocusLoss
            pauseOnHover
            theme="colored"
        />
        <BrowserRouter>
          <div className="min-vh-100 p-0 m-0">
            <Main/>
          </div>
        </BrowserRouter>
      </I18nextProvider>
  );
}

export default App;

export const cookServerInstance = axios.create({
    baseURL: 'http://localhost:8080/api/v1/',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    params: {}
});
export const cookbookServerStatus = 611;