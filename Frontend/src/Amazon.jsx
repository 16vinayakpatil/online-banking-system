import React from 'react';
import Card from './Cards';
import Sdata from './Sdata';
const Amazon =()=>{
    return(
        <Card

      imgscr={Sdata[2].imgscr}
      sname={Sdata[2].title}
      title={Sdata[2].sname}
      links={Sdata[2].links}
    />

    );
}
export default Amazon;