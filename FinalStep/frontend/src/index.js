import React from "react";
import {BrowserRouter as Router} from "react-router-dom";
import App from "./components/App";
import {createRoot} from "react-dom/client";

const container = document.getElementById('app')
const root = createRoot(container)
root.render(
    <Router>
        <App/>
    </Router>
);
