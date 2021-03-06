USE [master]
GO
/****** Object:  Database [AffiliateMarketingDB]    Script Date: 2/27/2019 9:53:19 PM ******/
CREATE DATABASE [AffiliateMarketingDB]
 CONTAINMENT = NONE
ALTER DATABASE [AffiliateMarketingDB] SET COMPATIBILITY_LEVEL = 130
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [AffiliateMarketingDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [AffiliateMarketingDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [AffiliateMarketingDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [AffiliateMarketingDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET  DISABLE_BROKER 
GO
ALTER DATABASE [AffiliateMarketingDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [AffiliateMarketingDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [AffiliateMarketingDB] SET  MULTI_USER 
GO
ALTER DATABASE [AffiliateMarketingDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [AffiliateMarketingDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [AffiliateMarketingDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [AffiliateMarketingDB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [AffiliateMarketingDB] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [AffiliateMarketingDB] SET QUERY_STORE = OFF
GO
USE [AffiliateMarketingDB]
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
USE [AffiliateMarketingDB]
GO
/****** Object:  Table [dbo].[Advertiser]    Script Date: 2/27/2019 9:53:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Advertiser](
	[advertiserID] [nvarchar](50) NOT NULL,
	[advertiserName] [nvarchar](50) NULL,
	[phone] [nvarchar](11) NULL,
	[email] [nvarchar](50) NULL,
	[address] [nvarchar](max) NULL,
	[website] [nvarchar](max) NULL,
 CONSTRAINT [PK_Advertiser] PRIMARY KEY CLUSTERED 
(
	[advertiserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Campaign]    Script Date: 2/27/2019 9:53:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Campaign](
	[campaignID] [nvarchar](50) NOT NULL,
	[campaignName] [nvarchar](50) NULL,
	[banner] [nvarchar](max) NULL,
	[campaignContent] [nvarchar](max) NULL,
	[advertiserID] [nvarchar](50) NULL,
	[startDate] [date] NULL,
	[endDate] [date] NULL,
	[percentDiscount] [float] NULL,
	[minBill] [float] NULL,
	[maxAmountDiscount] [float] NULL,
	[percentComission] [float] NULL,
	[isWorking] [bit] NULL,
 CONSTRAINT [PK_Campaign] PRIMARY KEY CLUSTERED 
(
	[campaignID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CampaignRegistration]    Script Date: 2/27/2019 9:53:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CampaignRegistration](
	[campaignID] [nvarchar](50) NOT NULL,
	[publisherID] [nvarchar](20) NOT NULL,
	[promotionCode] [nchar](15) NULL,
	[registerDate] [date] NULL,
 CONSTRAINT [PK_CampaignRegistration] PRIMARY KEY CLUSTERED 
(
	[campaignID] ASC,
	[publisherID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PromotionCode]    Script Date: 2/27/2019 9:53:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PromotionCode](
	[promotionCode] [nchar](15) NOT NULL,
	[percentEarn] [float] NULL,
	[isWorking] [bit] NULL,
 CONSTRAINT [PK_PromotionCode] PRIMARY KEY CLUSTERED 
(
	[promotionCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PromotionCodeTracking]    Script Date: 2/27/2019 9:53:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PromotionCodeTracking](
	[trackingId] [int] IDENTITY(1,1) NOT NULL,
	[promotionCode] [nchar](15) NOT NULL,
	[timeOfUsing] [datetime] NOT NULL,
	[totalAmoutOfOrder] [float] NOT NULL,
	[moneyEarned] [float] NOT NULL,
 CONSTRAINT [PK_PromotionCodeTracking] PRIMARY KEY CLUSTERED 
(
	[trackingId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Publisher]    Script Date: 2/27/2019 9:53:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Publisher](
	[publisherID] [nvarchar](20) NOT NULL,
	[password] [nvarchar](50) NULL,
	[lastname] [nvarchar](50) NULL,
	[firstname] [nvarchar](50) NULL,
	[phone] [nvarchar](15) NULL,
	[email] [nvarchar](50) NULL,
 CONSTRAINT [PK_Pulisher] PRIMARY KEY CLUSTERED 
(
	[publisherID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Advertiser] ([advertiserID], [advertiserName], [phone], [email], [address], [website]) VALUES (N'adverPassio', N'Passio', N'0327962333', N'passio@gmail.com', N'Quang Trung software park', NULL)
INSERT [dbo].[Advertiser] ([advertiserID], [advertiserName], [phone], [email], [address], [website]) VALUES (N'adverShopee', N'Shopee', N'0976088128', N'shopee@gmail.com', N'Nam Ky Khoi Nghia', N'shopee.vn')
INSERT [dbo].[Campaign] ([campaignID], [campaignName], [banner], [campaignContent], [advertiserID], [startDate], [endDate], [percentDiscount], [minBill], [maxAmountDiscount], [percentComission], [isWorking]) VALUES (N'campPassioMorning', N'Combo sáng ưu đãi', N'https://thue.today/media/images/section/brand/270_13340306-1257785227579833-2997907323004410665-o.jpg', NULL, N'adverPassio', CAST(N'2018-12-01' AS Date), CAST(N'2019-06-30' AS Date), 10, 300, 50, 4, 1)
INSERT [dbo].[Campaign] ([campaignID], [campaignName], [banner], [campaignContent], [advertiserID], [startDate], [endDate], [percentDiscount], [minBill], [maxAmountDiscount], [percentComission], [isWorking]) VALUES (N'campShopee83', N'Mỹ phẩm chính hãng', N'https://cf.shopee.vn/file/8478b10d2a7cd601c4dacf65a0862ba3', NULL, N'adverShopee', CAST(N'2019-02-01' AS Date), CAST(N'2019-06-01' AS Date), 15, 300, 60, 6, 1)
INSERT [dbo].[Campaign] ([campaignID], [campaignName], [banner], [campaignContent], [advertiserID], [startDate], [endDate], [percentDiscount], [minBill], [maxAmountDiscount], [percentComission], [isWorking]) VALUES (N'campShopeeFood', N'Ăn vụng là tuyệt nhất', N'https://cf.shopee.vn/file/147550b6d2ded55153155c2a3bfef0a1', NULL, N'adverShopee', CAST(N'2019-02-01' AS Date), CAST(N'2019-04-01' AS Date), 10, 200, 30, 5, 1)
INSERT [dbo].[CampaignRegistration] ([campaignID], [publisherID], [promotionCode], [registerDate]) VALUES (N'campShopee83', N'datnd', N'SHOPEEDATNDN   ', CAST(N'2019-02-27' AS Date))
INSERT [dbo].[CampaignRegistration] ([campaignID], [publisherID], [promotionCode], [registerDate]) VALUES (N'campShopee83', N'nguyendpt', N'SHOPEENGUYEN   ', CAST(N'2019-02-28' AS Date))
INSERT [dbo].[PromotionCode] ([promotionCode], [percentEarn], [isWorking]) VALUES (N'SHOPEEDATNDN   ', 6, 1)
INSERT [dbo].[PromotionCode] ([promotionCode], [percentEarn], [isWorking]) VALUES (N'SHOPEENGUYEN   ', 6, 1)
INSERT [dbo].[Publisher] ([publisherID], [password], [lastname], [firstname], [phone], [email]) VALUES (N'datnd', N'1234', N'Dat', N'Nguyen', N'0327962333', N'datnd@gmail.com')
INSERT [dbo].[Publisher] ([publisherID], [password], [lastname], [firstname], [phone], [email]) VALUES (N'nguyendpt', N'1234', N'Nguyen', N'Dang', N'012345678', N'nguyendpt@gmail.com')
INSERT [dbo].[Publisher] ([publisherID], [password], [lastname], [firstname], [phone], [email]) VALUES (N'vietnh', N'1234', N'Viet', N'Nguyen', N'098742182', N'vietnh@gmail.com')
USE [master]
GO
ALTER DATABASE [AffiliateMarketingDB] SET  READ_WRITE 
GO
