import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebookF, faTwitter, faInstagram } from '@fortawesome/free-brands-svg-icons';

const Footer = () => {
  return (
    <div>
      <footer style={styles.footer}>
          <div style={styles.container}>
              <div style={styles.column}>
                  <h3 style={styles.heading}>Contact Us</h3>
                  <p style={styles.text}>Email: info@example.com</p>
                  <p style={styles.text}>Phone: (123) 456-7890</p>
              </div>
              {/* <div style={styles.column}>
                  <h3 style={styles.heading}>Links</h3>
                  <ul style={styles.list}>
                      <li style={styles.listItem}>Home</li>
                      <li style={styles.listItem}>About</li>
                      <li style={styles.listItem}>Services</li>
                      <li style={styles.listItem}>Contact</li>
                  </ul>
              </div> */}
              {/* <div style={styles.column}>
                  <h3 style={styles.heading}>Social Media</h3>
                  <div style={styles.socialIcons}>
                      <a href="#" style={styles.iconLink}>
                          <FontAwesomeIcon icon={faFacebookF} />
                      </a>
                      <a href="#" style={styles.iconLink}>
                          <FontAwesomeIcon icon={faTwitter} />
                      </a>
                      <a href="#" style={styles.iconLink}>
                          <FontAwesomeIcon icon={faInstagram} />
                      </a>
                  </div>
              </div> */}
          </div>
        <p style={styles.copyRight}>© 2023 Your Company. All rights reserved.</p>
      </footer>
    </div>
  );
};

const styles = {
  footer: {
    backgroundColor: '#333',
    color: '#fff',
    padding: '40px 0',
    fontSize: '14px',
    textAlign: 'center'
  },
  container: {
    display: 'flex',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
    maxWidth: '1200px',
    margin: '0 auto',
  },
  column: {
    flex: '1 1 30%',
    marginBottom: '20px',
  },
  heading: {
    fontSize: '18px',
    fontWeight: 'bold',
    marginBottom: '10px',
  },
  text: {
    marginBottom: '5px',
  },
  list: {
    listStyle: 'none',
    padding: 0,
  },
  listItem: {
    marginBottom: '5px',
    cursor: 'pointer',
  },
  socialIcons: {
    justifyContent: 'flex-start',
    marginTop: '10px',
  },
  iconLink: {
    color: '#fff',
    marginRight: '10px',
    fontSize: '16px',
  },
  copyRight: {
    marginTop: '20px',
    textAlign: 'center',
  },
};

export default Footer;
