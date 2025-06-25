import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import config from './Configuration/config';

const ListEmployeeComponent = ({ transactions }) => {
  return (
    <div className="container">
      <h2 className="text-center">All Transactions</h2>
      <div style={{ overflowY: 'scroll', maxHeight: '360px' }}>
        <table className="table table-bordered table-striped">
          <thead>
            <tr>
              <th>Transaction ID:</th>
              <th>Date/Time</th>
              <th>Acc ID</th>
              <th>Acc Number</th>
              <th>Amount</th>
              <th>Description</th>
              <th>Type</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((transaction) => (
              <tr key={transaction.transactionid}>
                <td>{transaction.transactionid}</td>
                <td>{transaction.datetime}</td>
                <td>{transaction.accid}</td>
                <td>{transaction.accno}</td>
                <td>{transaction.amount}</td>
                <td>{transaction.description}</td>
                <td>{transaction.transactiontype}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

const TransactionListPage = () => {
  const [transactions, setTransactions] = useState([]);
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  useEffect(() => {
    fetchTransactions();
  }, []);

  const fetchTransactions = async () => {
    try {
      const token = localStorage.getItem('jwt');
      const response = await axios.get(`http://localhost:${config.port}/transaction/employee/show-all-transactions`, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      const formattedTransactions = response.data.map((transaction) => ({
        ...transaction,
        datetime: new Date(transaction.datetime).toLocaleString('en-US', {
          year: 'numeric',
          month: '2-digit',
          day: '2-digit',
          hour: '2-digit',
          minute: '2-digit',
        }),
      }));

      const sortedTransactions = formattedTransactions.sort((a, b) => new Date(a.datetime) - new Date(b.datetime));

      setTransactions(sortedTransactions);
    } catch (error) {
      console.error('Failed to retrieve transactions:', error);
    }
  };

  const handleFilterByDate = async () => {
    try {
      const token = localStorage.getItem('jwt');
      const response = await axios.get(`http://localhost:${config.port}/transaction/customer/filterbydate`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          start: startDate,
          end: endDate,
        },
      });

      const filteredTransactions = response.data.map((transaction) => ({
        ...transaction,
        datetime: new Date(transaction.datetime).toLocaleString('en-US', {
          year: 'numeric',
          month: '2-digit',
          day: '2-digit',
          hour: '2-digit',
          minute: '2-digit',
        }),
      }));
      const sortedTransactions = filteredTransactions.sort(
        (a, b) => new Date(a.datetime) - new Date(b.datetime)
      );

      setTransactions(filteredTransactions);

    } catch (error) {
      console.error('Failed to filter transactions by date:', error);
    }
  };

  const handleResetFilter = () => {
    setStartDate('');
    setEndDate('');
    fetchTransactions();
  };

  const handleDownloadPDF = async () => {
    try {
      const token = localStorage.getItem('jwt');
      const endpoint = startDate && endDate ? '/exportfilter' : '/export';

      const response = await axios.get(`http://localhost:${config.port}/transaction/customer${endpoint}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        responseType: 'blob',
        params: {
          start: startDate,
          end: endDate,
        },
      });

      const blob = new Blob([response.data], { type: 'application/pdf' });
      const url = URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', 'transactions.pdf');
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);

      // Show toast notification
      toast.success('PDF downloaded successfully!');
    } catch (error) {
      console.error('Failed to download PDF:', error);
      toast.error('Failed to download PDF');
    }
  };

  return (
    <div>
      <div className="container">
        <div className="row align-items-center">
          <div className="col">
            <label style={{color:'black'}}>Start Date:</label>
            <input type="text" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
          </div>
          <div className="col">
            <label style={{color:'black'}}>End Date:</label>
            <input type="text" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
          </div>
          <div className="col">
            <button className="btn btn-primary" onClick={handleFilterByDate}>Filter By Date</button>
            <button className="btn btn-secondary" onClick={handleResetFilter}>Reset</button>
          </div>
        </div>
      </div>
      <ListEmployeeComponent transactions={transactions} />
      <button
        onClick={handleDownloadPDF}
        style={{
          display: 'block',
          margin: ' 8px auto',
          height: '40px',
          width: '135px',
          color: 'white',
          backgroundColor: 'green',
          borderRadius: '5px',
          borderColor: 'grey',
        }}
      >
        Download PDF
      </button>
      <ToastContainer />
    </div>
  );
};

export default TransactionListPage;
