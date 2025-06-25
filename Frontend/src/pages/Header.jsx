import React from "react";
import { Link } from "react-router-dom";
import BannerImage from "../assets/scott-graham-5fNmWej4tAA-unsplash.jpg";
import "../styles/Header.css";

function Header() {
  return (
    <div className="home" style={{ backgroundImage: `url(${BannerImage})` }}>
      <div className="headerContainer">
        {/* <h1 style={{textAlign:'center',color:'black',height:'200px'}}>Welcome To My Bank</h1> */}
        {/* <p> Get Started</p> */}
        <Link to="/menu">
          {/* <button> ORDER NOW </button> */}
        </Link>
      </div>
    </div>
  );
}

export default Header;
