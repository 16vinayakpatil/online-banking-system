import React from 'react';
import Card from './Cards';
import Sdata from './Sdata';
const Netflix =()=>{
    return(
        <Card

      imgscr={Sdata[0].imgscr}
      sname={Sdata[0].title}
      title={Sdata[0].sname}
      links={Sdata[0].links}
    />

    );
}
export default Netflix;