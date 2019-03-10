using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Affiliate_Marketing_API.Models
{
    public class CampaignRegistrationObject
    {
        public string campaignID { get; set; }
        public string publisherID { get; set; }
        public string promotionCode { get; set; }
    }
}