


/*
 *
 *
 *
 

in binary:
every nibble is divided in this way:

b0: function for sys
b1: function for phase F1
b2: function for phase F2
b3: function for phase F3

phases are as follows:

nibble 0:
00 00 00 00 00 00 00 X0 = no signs alterations
00 00 00 00 00 00 00 X1 = change sign to Sys 
00 00 00 00 00 00 00 X2 = change sign to F1  
00 00 00 00 00 00 00 X4 = change sign to F2 
00 00 00 00 00 00 00 X8 = change sign to F3
.....

nibble 1:
00 00 00 00 00 00 00 1X = exchange totals: import with export on Sys field; total is still Sys
00 00 00 00 00 00 00 2X = exchange F1 Import with F1 Export; total is F1 + F2 + F3
00 00 00 00 00 00 00 4X = exchange F2 Import with F2 Export; total is F1 + F2 + F3 
00 00 00 00 00 00 00 8X = exchange F3 Import with F3 Export; total is F1 + F2 + F3
....




 *
 *
 *
 * 
 */

//
//
// sum proper energy term according to the given meter read mode
//
// previously called this way
//
// var totalImportOffset = sumPartsOrWorkOnTotals(sumModeFlag,signF1, signF2, signF3, signFSys, colConsForF1, colConsForF2, colConsForF3, colConsForFSys, timeSeries[0]);
// var totalExportOffset = sumPartsOrWorkOnTotals(sumModeFlag,signF1, signF2, signF3, signFSys, colProdForF1, colProdForF2, colProdForF3, colProdForFSys, timeSeries[0]);
//
// function sumPartsOrWorkOnTotals(sumModeFlag, signF1, signF2, signF3, signFSys, colF1, colF2, colF3, colFSys, theRow) {
//
//
//

function sumPartsOrWorkOnTotals(billMeterOnEdgeDescriptor, theRow) {
  const valCF1 = theRow[billMeterOnEdgeDescriptor.colConsForF1]; // ?? 0;
  const valCF2 = theRow[billMeterOnEdgeDescriptor.colConsForF2]; // ?? 0;
  const valCF3 = theRow[billMeterOnEdgeDescriptor.colConsForF3]; // ?? 0;
  const valCFSys = theRow[billMeterOnEdgeDescriptor.colConsForFSys]; // ?? 0;

  const valPF1 = theRow[billMeterOnEdgeDescriptor.colProdForF1]; // ?? 0;
  const valPF2 = theRow[billMeterOnEdgeDescriptor.colProdForF2]; // ?? 0;
  const valPF3 = theRow[billMeterOnEdgeDescriptor.colProdForF3]; // ?? 0;
  const valPFSys = theRow[billMeterOnEdgeDescriptor.colProdForFSys]; // ?? 0;


  const consumption = (billMeterOnEdgeDescriptor.sumModeFlag ? 
    billMeterOnEdgeDescriptor.signF1 * valCF1 
    + billMeterOnEdgeDescriptor.signF2 * valCF2 
    + billMeterOnEdgeDescriptor.signF3 * valCF3 
    : 
    billMeterOnEdgeDescriptor.signFSys * valCFSys);  
  const production = (billMeterOnEdgeDescriptor.sumModeFlag ? 
    billMeterOnEdgeDescriptor.signF1 * valPF1
    + billMeterOnEdgeDescriptor.signF2 * valPF2
    + billMeterOnEdgeDescriptor.signF3 * valPF3 
    : 
    billMeterOnEdgeDescriptor.signFSys * valPFSys);  
  
  return {
    consumption,
    production
  };
}


  /*
  // assign fieds for import and export and sign term
  //
  // F1 
  const invertConsAndProdFlagF1 = (meterReadMode & 0x20);
  const colProdForF1 = invertConsAndProdFlagF1 ? "ConsumptionF1" : "ProductionF1";
  const colConsForF1 = invertConsAndProdFlagF1 ? "ProductionF1" : "ConsumptionF1";
  const signF1 = (meterReadMode & 0x02) ? -1 : 1;
  // F2 
  const invertConsAndProdFlagF2 = (meterReadMode & 0x40);
  const colProdForF2 = invertConsAndProdFlagF2 ? "ConsumptionF2" : "ProductionF2";
  const colConsForF2 = invertConsAndProdFlagF2 ? "ProductionF2" : "ConsumptionF2";
  const signF2 = meterReadMode & 0x04 ? -1 : 1;
  // F3 
  const invertConsAndProdFlagF3 = (meterReadMode & 0x80);
  const colProdForF3 = invertConsAndProdFlagF3 ? "ConsumptionF3" : "ProductionF3";
  const colConsForF3 = invertConsAndProdFlagF3 ? "ProductionF3" : "ConsumptionF3";
  const signF3 = meterReadMode & 0x08 ? -1 : 1;
  // FSys 
  const invertConsAndProdFlagFSys = (meterReadMode & 0x10);
  const colProdForFSys =  invertConsAndProdFlagFSys ? "ConsumptionSys" : "ProductionSys";
  const colConsForFSys =  invertConsAndProdFlagFSys ? "ProductionSys" : "ConsumptionSys";
  const signFSys = meterReadMode & 0x01 === 0x01 ? -1 : 1;
  // check if measure comes from components or from Sys/total
  const sumModeFlag = (meterReadMode & 0xE0);
  */
function  buildMeterReadModes(theMeter){
  //
  // basic data for all the following
  const meterNameOnEdge = theMeter.MeterOnEdge;
  const metForFld = `${meterNameOnEdge}_`;
  const meterReadMode = theMeter.ReadMode;
  // check if measure comes from components or from Sys/total
  const sumModeFlag = (meterReadMode & 0xE0);

  // flags invert Introductions with productions
  const invertConsAndProdFlagF1 = (meterReadMode & 0x20);
  const invertConsAndProdFlagF2 = (meterReadMode & 0x40);
  const invertConsAndProdFlagF3 = (meterReadMode & 0x80);
  const invertConsAndProdFlagFSys = (meterReadMode & 0x10);


  //
  //
  // assign fieds for:
  //  - import
  //  - export
  //  - sign term
  //
  const meterDescriptor = {
    // 
    theMeter: theMeter,
    meterNameOnEdge: meterNameOnEdge,
    meterReadMode: meterReadMode,
    sumModeFlag: sumModeFlag,
    // F1
    invertConsAndProdFlagF1: invertConsAndProdFlagF1,
    colProdForF1: invertConsAndProdFlagF1 ? `${metForFld}ConsumptionF1` : `${metForFld}ProductionF1`,
    colConsForF1: invertConsAndProdFlagF1 ? `${metForFld}ProductionF1` : `${metForFld}ConsumptionF1`,
    signF1: (meterReadMode & 0x02) ? -1 : 1,
    // F2 
    invertConsAndProdFlagF2: (meterReadMode & 0x40),
    colProdForF2: invertConsAndProdFlagF2 ? `${metForFld}ConsumptionF2` : `${metForFld}ProductionF2`,
    colConsForF2: invertConsAndProdFlagF2 ? `${metForFld}ProductionF2` : `${metForFld}ConsumptionF2`,
    signF2: meterReadMode & 0x04 ? -1 : 1,
    // F3 
    invertConsAndProdFlagF3: (meterReadMode & 0x80),
    colProdForF3: invertConsAndProdFlagF3 ? `${metForFld}ConsumptionF3` : `${metForFld}ProductionF3`,
    colConsForF3: invertConsAndProdFlagF3 ? `${metForFld}ProductionF3` : `${metForFld}ConsumptionF3`,
    signF3: meterReadMode & 0x08 ? -1 : 1,
    // FSys 
    invertConsAndProdFlagFSys: (meterReadMode & 0x10),
    colProdForFSys:  invertConsAndProdFlagFSys ? `${metForFld}ConsumptionSys` : `${metForFld}ProductionSys`,
    colConsForFSys:  invertConsAndProdFlagFSys ? `${metForFld}ProductionSys` : `${metForFld}ConsumptionSys`,
    signFSys: meterReadMode & 0x01 === 0x01 ? -1 : 1

  };
 
  return meterDescriptor;
}


//
// having an Influxdb time series, calculate the total energy used 
//
function buildTotalsForMeter(totalsContainer, theMeterWeAreBilling, timeSeries, introMeterOnEdgeDescriptor, prodMeterOnEdgeDescriptor, timeStep) {
  /*
  // assign fieds for import and export and sign term
  //
  // F1 
  const invertConsAndProdFlagF1 = (meterReadMode & 0x20);
  const colProdForF1 = invertConsAndProdFlagF1 ? "ConsumptionF1" : "ProductionF1";
  const colConsForF1 = invertConsAndProdFlagF1 ? "ProductionF1" : "ConsumptionF1";
  const signF1 = (meterReadMode & 0x02) ? -1 : 1;
  // F2 
  const invertConsAndProdFlagF2 = (meterReadMode & 0x40);
  const colProdForF2 = invertConsAndProdFlagF2 ? "ConsumptionF2" : "ProductionF2";
  const colConsForF2 = invertConsAndProdFlagF2 ? "ProductionF2" : "ConsumptionF2";
  const signF2 = meterReadMode & 0x04 ? -1 : 1;
  // F3 
  const invertConsAndProdFlagF3 = (meterReadMode & 0x80);
  const colProdForF3 = invertConsAndProdFlagF3 ? "ConsumptionF3" : "ProductionF3";
  const colConsForF3 = invertConsAndProdFlagF3 ? "ProductionF3" : "ConsumptionF3";
  const signF3 = meterReadMode & 0x08 ? -1 : 1;
  // FSys 
  const invertConsAndProdFlagFSys = (meterReadMode & 0x10);
  const colProdForFSys =  invertConsAndProdFlagFSys ? "ConsumptionSys" : "ProductionSys";
  const colConsForFSys =  invertConsAndProdFlagFSys ? "ProductionSys" : "ConsumptionSys";
  const signFSys = meterReadMode & 0x01 === 0x01 ? -1 : 1;
  // check if measure comes from components or from Sys/total
  const sumModeFlag = (meterReadMode & 0xE0);
  */
  const keyForThisMeterResult = "meter_" + theMeterWeAreBilling.meterid;
  totalsContainer.KWHTotals.billingTotals[keyForThisMeterResult] = {
    billAtStart:{
      consumption: 0
      , production: 0
    },
    billAtEnd: {
      consumption: 0
      , production: 0
    },
    totalKWHBill_intro: 0,
    totalKWHBill_partFromProd: 0,
    totalKWHBill_partFromIntro: 0,
    totalKWHBill_prod: 0,
    meter: theMeterWeAreBilling

  };
  theMeterWeAreBilling.steppi = [];

  const billMeterOnEdgeDescriptor = buildMeterReadModes(theMeterWeAreBilling);
  const meterInOffsetFlag = theMeterWeAreBilling.ReadMode & 0xE0;
  //
  // loop through the results
  //
  //   former impl:
  // 
  // var totalImportOffset = sumPartsOrWorkOnTotals(sumModeFlag,signF1, signF2, signF3, signFSys, colConsForF1, colConsForF2, colConsForF3, colConsForFSys, timeSeries[0]);
  // var totalExportOffset = sumPartsOrWorkOnTotals(sumModeFlag,signF1, signF2, signF3, signFSys, colProdForF1, colProdForF2, colProdForF3, colProdForFSys, timeSeries[0]);
  //
  // new buffers startup
  var totalBillPreviousStep = sumPartsOrWorkOnTotals(billMeterOnEdgeDescriptor, timeSeries[0]);
  totalsContainer.KWHTotals.introAtStart = totalsContainer.KWHTotals.introAtStart ?? sumPartsOrWorkOnTotals(introMeterOnEdgeDescriptor, timeSeries[0]);
  totalsContainer.KWHTotals.prodAtStart = totalsContainer.KWHTotals.prodAtStart ?? sumPartsOrWorkOnTotals(prodMeterOnEdgeDescriptor, timeSeries[0]);
  totalsContainer.KWHTotals.billingTotals[keyForThisMeterResult].billAtStart = totalBillPreviousStep;
  totalsContainer.KWHTotals.totalKWHBill_intro = 0;
  totalsContainer.KWHTotals.totalKWHBill_prod = 0;
  buildOffsetMeter(theMeterWeAreBilling
    , totalsContainer.KWHTotals.offsetMeter.totalBillingOffset_allParts
    , totalsContainer.KWHTotals.offsetMeter.totalBillingOffset_intro
    , totalsContainer.KWHTotals.offsetMeter.totalBillingOffset_prod
    , 0
    , 0, 0, 0);

  totalsContainer.KWHTotals.introArray = totalsContainer.KWHTotals.introArray ?? [totalsContainer.KWHTotals.introAtStart];
  totalsContainer.KWHTotals.prodArray = totalsContainer.KWHTotals.prodArray ?? [totalsContainer.KWHTotals.prodAtStart]; // Array(1).fill(-1);

  // let lastRow = timeSeries[0];
  let yyy = 0;
  let lastRow = timeSeries[0];
  for(yyy = timeStep; yyy < timeSeries.length; yyy += timeStep) {
    lastRow = evalBillingEvent(yyy, yyy - timeStep, keyForThisMeterResult, billMeterOnEdgeDescriptor, meterInOffsetFlag, totalBillPreviousStep, totalsContainer, theMeterWeAreBilling, timeSeries, introMeterOnEdgeDescriptor, prodMeterOnEdgeDescriptor);
  }
  //
  //if the last one was not calculated in the billing, calulate the last one with the last one of the cyclde  
  if (lastRow !== timeSeries[timeSeries.length - 1]){
    console.log("stop");
    lastRow = evalBillingEvent(timeSeries.length - 1, yyy - timeStep, keyForThisMeterResult, billMeterOnEdgeDescriptor, meterInOffsetFlag, totalBillPreviousStep, totalsContainer, theMeterWeAreBilling, timeSeries, introMeterOnEdgeDescriptor, prodMeterOnEdgeDescriptor);
  }
  // set final values as red from db at the end...
  totalsContainer.KWHTotals.billingTotals[keyForThisMeterResult].billAtEnd = totalBillPreviousStep;
  // check the totals with the last value in the array timeseries
  totalsContainer.KWHTotals.introAtEnd = totalsContainer.KWHTotals.introArray[timeSeries.length -1] ;
  totalsContainer.KWHTotals.prodAtEnd = totalsContainer.KWHTotals.prodArray[timeSeries.length -1];


  return meterInOffsetFlag ? 1 : 0;
}

//
//function to calculate the billing. 
//takes a row and the previous row as input and make some calculation

function evalBillingEvent( theRowNumber
        , lastRowNumber
        , keyForThisMeterResult
        , billMeterOnEdgeDescriptor
        , meterInOffsetFlag
        , totalBillPreviousStep
        , totalsContainer
        , theMeterWeAreBilling
        , timeSeries
        , introMeterOnEdgeDescriptor
        , prodMeterOnEdgeDescriptor){
    const theRow = timeSeries[theRowNumber];
    // billing meter readings
    var totalBillAtThisStep = sumPartsOrWorkOnTotals(billMeterOnEdgeDescriptor, theRow);
    totalsContainer.KWHTotals.introArray[theRowNumber] = totalsContainer.KWHTotals.introArray[theRowNumber] ?? sumPartsOrWorkOnTotals(introMeterOnEdgeDescriptor, theRow);
    totalsContainer.KWHTotals.prodArray[theRowNumber] = totalsContainer.KWHTotals.prodArray[theRowNumber] ?? sumPartsOrWorkOnTotals(prodMeterOnEdgeDescriptor, theRow);
    //
    // eval intro
    totalsContainer.KWHTotals.totalKWHImport_intro = totalsContainer.KWHTotals.introArray[theRowNumber].consumption - totalsContainer.KWHTotals.introArray[lastRowNumber].consumption;
    totalsContainer.KWHTotals.totalKWHImport_prod = totalsContainer.KWHTotals.introArray[theRowNumber].production - totalsContainer.KWHTotals.introArray[lastRowNumber].production;
    //
    totalsContainer.KWHTotals.totalKWHExport_intro = totalsContainer.KWHTotals.prodArray[theRowNumber].consumption - totalsContainer.KWHTotals.prodArray[lastRowNumber].consumption;
    totalsContainer.KWHTotals.totalKWHExport_prod = totalsContainer.KWHTotals.prodArray[theRowNumber].production - totalsContainer.KWHTotals.prodArray[lastRowNumber].production;
    //
    //
    // evaluate how much power from the solar panels goes to the users and how much goes to the grid....
    let totalSolarPowerToUsers = totalsContainer.KWHTotals.totalKWHExport_prod - totalsContainer.KWHTotals.totalKWHImport_prod;
    //
    // this is the part of energy (active) we give back to the grid
    // is not used here, but could be interesting to log it....
    let totalSolarPowerToGrid = totalsContainer.KWHTotals.totalKWHExport_prod - totalSolarPowerToUsers;
    // .. and is supposed to be equal to the following
    let assertForExportedEnergyToGrid = totalsContainer.KWHTotals.totalKWHImport_prod - totalSolarPowerToGrid;
    if (assertForExportedEnergyToGrid > 0.01) {
      console.log(`failed assert for \ntotalsContainer.KWHTotals.totalKWHImport_prod === totalSolarPowerToGrid\n Problem was at theRowNumber= ${theRowNumber}; row follows... `, theRow);
    }
    //
    //
    // some overall evals in this step between total imported and exported energy
    //
    // in the following two steps we calculate the percentage of solar and introduced power available to the users
    //
    // power for the user (solar + grid)
    const totalPowerForUsers = totalSolarPowerToUsers + totalsContainer.KWHTotals.totalKWHImport_intro;
    // total solar eneergy over total energy for users (normalized percentage)
    totalsContainer.KWHTotals.percentIntroAndSolar4User_solar = totalPowerForUsers != 0 
                                                            ? 
                                                              totalSolarPowerToUsers / totalPowerForUsers 
                                                            : 
                                                              0;
    // total introduced energy over total energy for users (normalized percentage)
    totalsContainer.KWHTotals.percentIntroAndSolar4User_intro = totalPowerForUsers != 0 
                                                            ? 
                                                              totalsContainer.KWHTotals.totalKWHImport_intro / totalPowerForUsers
                                                            : 
                                                              0;

    let assertForNormsPercs = (1 - totalsContainer.KWHTotals.percentIntroAndSolar4User_solar) - totalsContainer.KWHTotals.percentIntroAndSolar4User_intro;
    if (
          (assertForNormsPercs > 0.01) 
          && (
            (totalsContainer.KWHTotals.percentIntroAndSolar4User_solar != 0)
            || 
            (totalsContainer.KWHTotals.percentIntroAndSolar4User_intro != 0)
          )
      ) {
      console.log(`failed assert for \n(1 - totalsContainer.KWHTotals.percentIntroAndSolar4User_solar) - totalsContainer.KWHTotals.percentIntroAndSolar4User_intro\n Problem was at theRowNumber= ${theRowNumber}; row follows... `, theRow);
    }

    // this billing meter levels...
    const thisStepBill_intro = totalBillAtThisStep.consumption - totalBillPreviousStep.consumption;
    const thisStepBill_prod = totalBillAtThisStep.production - totalBillPreviousStep.production;; // this is expected to be zero (unless the user puts some enrgy on the net....

    // the result
    totalsContainer.KWHTotals.billingTotals[keyForThisMeterResult].totalKWHBill_intro += thisStepBill_intro;
    
    const userPow_fromProd = thisStepBill_intro * totalsContainer.KWHTotals.percentIntroAndSolar4User_solar;
    const userPow_fromIntro = thisStepBill_intro * (1 - totalsContainer.KWHTotals.percentIntroAndSolar4User_solar);
    
    totalsContainer.KWHTotals.billingTotals[keyForThisMeterResult].totalKWHBill_partFromProd += userPow_fromProd;
    totalsContainer.KWHTotals.billingTotals[keyForThisMeterResult].totalKWHBill_partFromIntro += userPow_fromIntro;
    // safety check: abnormal values ?!?
    if (Math.abs(userPow_fromProd) > 100) {
      console.log("ahia");
    }                                   
    //20220602 Manuel & Enrico maybe it has to go back to the way it was before
    //remove totalPowerForUsers and use thisStepBill_intro                                   
    buildOffsetMeter(theMeterWeAreBilling
                      , totalsContainer.KWHTotals.offsetMeter.totalBillingOffset_allParts
                      , totalsContainer.KWHTotals.offsetMeter.totalBillingOffset_intro
                      , totalsContainer.KWHTotals.offsetMeter.totalBillingOffset_prod
                      , theRowNumber
                      , totalPowerForUsers, userPow_fromIntro, userPow_fromProd, meterInOffsetFlag);
    // if the meter is in offest, calculate steppi    
    if(meterInOffsetFlag){
      // console.log(keyForThisMeterResult);
      //eval kappa for this step
      const steppo = {
        thisStepBill_intro,
        userPow_fromProd,
        userPow_fromIntro
      };
      theMeterWeAreBilling.steppi[theRowNumber] = steppo;
    }

    // should be always zero or near to zero
    totalsContainer.KWHTotals.billingTotals[keyForThisMeterResult].totalKWHBill_prod += thisStepBill_prod;

    //
    // update offsets
    totalBillPreviousStep.production = totalBillAtThisStep.production;
    totalBillPreviousStep.consumption = totalBillAtThisStep.consumption;
    // totalIntroPreviousStep = sumPartsOrWorkOnTotals(introMeterOnEdgeDescriptor, theRow);
    // totalProdPreviousStep = sumPartsOrWorkOnTotals(prodMeterOnEdgeDescriptor, theRow);
   return theRow;
}


function buildOffsetMeter(theMeterWeAreBilling
                          , totalBillingOffset_allParts, totalBillingOffset_intro, totalBillingOffset_prod, theRowNumber
                          , userPow_total, userPow_fromIntro, userPow_fromProd, meterInOffsetFlag){
  if (meterInOffsetFlag) {
    return;
  }

  //
  //
  totalBillingOffset_allParts[theRowNumber] = userPow_total +
                                        (totalBillingOffset_allParts[theRowNumber] ?? 0);
  totalBillingOffset_intro[theRowNumber] = userPow_fromIntro +                                                           
                                        (totalBillingOffset_intro[theRowNumber] ?? 0) ;                                                             
  totalBillingOffset_prod[theRowNumber] = userPow_fromProd + 
                                        (totalBillingOffset_prod[theRowNumber] ?? 0) ;
  
}


function calculateMeterInOffSet(meterInOffset, KWHTotals, timeStep){
  const keyToMeter = "meter_" + meterInOffset.meterid;
  const valuesInOffset = {
    billAtEnd: {
      consumption: 0
      , production: 0
    }
    ,billAtStart:{
      consumption: 0
      , production: 0
    }
    , totalKWHBill_intro: 0
    , totalKWHBill_partFromIntro: 0
    , totalKWHBill_partFromProd: 0
    , totalKWHBill_prod:0
    , kappa : []
  };
  KWHTotals.billingTotals[keyToMeter].valuesInOffset = valuesInOffset;



  for (let xxx = timeStep; xxx < KWHTotals.introArray.length; xxx += timeStep) {
    //
    // eval intro
    const mainIntro_cons = KWHTotals.introArray[xxx].consumption - KWHTotals.introArray[xxx - timeStep].consumption;
    const mainIntro_prod = KWHTotals.introArray[xxx].production - KWHTotals.introArray[xxx - timeStep].production;
    //
    // eval prod
    const mainProd_prod = KWHTotals.prodArray[xxx].production - KWHTotals.prodArray[xxx - timeStep].production;
    const mainprod_prod4users = mainProd_prod - mainIntro_prod;

    //
    // eval meter under offset ....
    valuesInOffset.totalKWHBill_intro += mainIntro_cons + mainprod_prod4users - KWHTotals.offsetMeter.totalBillingOffset_allParts[xxx];
    valuesInOffset.totalKWHBill_partFromIntro += mainIntro_cons - KWHTotals.offsetMeter.totalBillingOffset_intro[xxx];
    valuesInOffset.totalKWHBill_partFromProd += mainprod_prod4users - KWHTotals.offsetMeter.totalBillingOffset_prod[xxx];
    

  }
  console.log("meter in offset was computed");

}


//
//
// read influx query results and evaluate total consumptions
// on every billed edge meter
//
//
function buildTotals(theEdgeForThisMeas, result, timeStep) {
/**
 * 
 *     
    idEdge: meterContainer.idedge,
    edgeDesc: meterContainer.edgedesc,
    billingMeters: [],
    testMeters: [],
    introduction: {},
    production: {},
    // some metas
    // const meterReadMode = theMeter["ReadMode"];
    // const meterOnEdge = theMeter["MeterOnEdge"];
    influxDb: meterContainer.InfluxDb,
    influxQuery: "", // = ypInflxQueries.generateInfluxDbQuery(meterOnEdge, measureDateStart, measureDateEnd);
    influxQueryResult: [],
    KWHTotals: {
              totalKWHImport : 0,
              totalKWHExport : 0
            }


    //
    // in the next future something will be added
    //

 * 
 * 
 */
  const billingMeters = theEdgeForThisMeas.billingMeters;
  theIntroMeter = theEdgeForThisMeas.introductionMeter;
  theProdsMeter = theEdgeForThisMeas.productionMeter;
  const introMeterOnEdgeDescriptor = buildMeterReadModes(theIntroMeter);
  const prodMeterOnEdgeDescriptor = buildMeterReadModes(theProdsMeter);

  let meterMarker = -1;
  for (xxx = 0; xxx < billingMeters.length; xxx++) {
    theMeterWeAreBilling = billingMeters[xxx];
    const _meterMarker = buildTotalsForMeter(theEdgeForThisMeas
                                      , theMeterWeAreBilling
                                      , result
                                      , introMeterOnEdgeDescriptor
                                      , prodMeterOnEdgeDescriptor
                                      , timeStep) > 0 ? xxx : -1;
    meterMarker =_meterMarker >= 0 ? xxx : meterMarker; 

  }
  if (meterMarker >= 0) {
    console.log(`meter ${meterMarker} is in offset mode ....`);
    const meterInOffset = billingMeters[meterMarker];
    meterInOffset.inOffsetFlag = true;

    calculateMeterInOffSet(meterInOffset, theEdgeForThisMeas.KWHTotals, timeStep);
  }
}



module.exports = {
  sumPartsOrWorkOnTotals,
  buildTotals
}