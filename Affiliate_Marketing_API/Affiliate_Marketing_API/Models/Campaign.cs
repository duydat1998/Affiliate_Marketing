//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Affiliate_Marketing_API.Models
{
    using System;
    using System.Collections.Generic;
    
    public partial class Campaign
    {
        public string campaignID { get; set; }
        public string campaignName { get; set; }
        public string banner { get; set; }
        public string campaignContent { get; set; }
        public string advertiserID { get; set; }
        public Nullable<System.DateTime> startDate { get; set; }
        public Nullable<System.DateTime> endDate { get; set; }
        public Nullable<double> percentDiscount { get; set; }
        public Nullable<double> minBill { get; set; }
        public Nullable<double> maxAmountDiscount { get; set; }
        public Nullable<double> percentComission { get; set; }
        public Nullable<bool> isWorking { get; set; }
    }
}
