import React from "react";
import BannerImage from "../assets/vendor1.png";
import "../styles/Header1.css";

function Header() {
  return (
    <div className="home" style={{ backgroundImage: `url(${BannerImage})` }}>
      <div className="headerContainer">
       </div>
    </div>
  );
}

export default Header;
