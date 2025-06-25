import React from "react";
import MultiplePizzas from "../assets/vendor.png";
import "../styles/About.css";
function About() {
  return (
    <div className="about">
      <div
        className="aboutTop"
        style={{ backgroundImage: `url(${MultiplePizzas})` }}
      ></div>
      <div className="aboutBottom">
        <h1> ABOUT US</h1>
        
        <p>
        Welcome to Vendor Productivity Manager, your all-in-one solution for streamlining vendor productivity tracking and management. We understand the challenges businesses face when managing vendors and tracking their productivity efficiently. Our tool is designed to simplify the process, ensuring a seamless workflow for vendors, team leaders, and administrators alike.

      <h1>  OUR MISSION </h1>
<p>
At Vendor Productivity Manager, our mission is to empower businesses to optimize their vendor management process. We believe that effective vendor productivity tracking is vital for the success of any organization. Our platform is tailored to enhance collaboration between vendors and teams, allowing for better decision-making and improved efficiency.
</p>
        </p>
      </div>
    </div>
  );
}

export default About;
