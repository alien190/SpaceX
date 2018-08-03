package com.example.data.model;

import java.util.List;

public class ServerResponse {


    /**
     * flight_number : 58
     * launch_year : 2018
     * launch_date_unix : 1522419231
     * launch_date_utc : 2018-03-30T14:13:51Z
     * launch_date_local : 2018-03-30T07:13:51-08:00
     * rocket : {"rocket_id":"falcon9","rocket_name":"Falcon 9","rocket_type":"FT","first_stage":{"cores":[{"core_serial":"B1041","flight":2,"block":4,"reused":true,"land_success":null,"landing_type":null,"landing_vehicle":null}]},"second_stage":{"payloads":[{"payload_id":"Iridium NEXT 5","reused":false,"customers":["Iridium Communications"],"payload_type":"Satellite","payload_mass_kg":9600,"payload_mass_lbs":21164.38,"orbit":"PO"}]}}
     * telemetry : {"flight_club":"https://www.flightclub.io/results/?code=IRD5"}
     * reuse : {"core":true,"side_core1":false,"side_core2":false,"fairings":false,"capsule":false}
     * launch_site : {"site_id":"vafb_slc_4e","site_name":"VAFB SLC 4E","site_name_long":"Vandenberg Air Force Base Space Launch Complex 4E"}
     * launch_success : true
     * links : {"mission_patch":"https://i.imgur.com/QUSoLHy.png","reddit_campaign":"https://www.reddit.com/r/spacex/comments/82njj5/iridium_next_constellation_mission_5_launch/","reddit_launch":"https://www.reddit.com/r/spacex/comments/88184i/rspacex_iridium_next_5_official_launch_discussion/","reddit_recovery":null,"reddit_media":"https://www.reddit.com/r/spacex/comments/88114l/rspacex_iridium5_media_thread_videos_images_gifs/","presskit":"http://www.spacex.com/sites/spacex/files/iridium-5_press_kit.pdf","article_link":"https://spaceflightnow.com/2018/03/30/iridium-messaging-network-gets-another-boost-from-spacex/","video_link":"https://www.youtube.com/watch?v=mp0TW8vkCLg"}
     * details : Fifth Iridium NEXT mission to deploy ten Iridium NEXT satellites. Reused booster from third Iridium flight, and although controlled descent was performed, the booster was expended into the ocean. SpaceX planned a second recovery attempt of one half of the fairing using the specially modified boat Mr. Steven. However, the fairing's parafoil twisted during the recovery, which led to water impact at high speed
     */

    private int flight_number;
    private String launch_year;
    private int launch_date_unix;
    private String launch_date_utc;
    private String launch_date_local;
    private RocketBean rocket;
    private TelemetryBean telemetry;
    private ReuseBean reuse;
    private LaunchSiteBean launch_site;
    private boolean launch_success;
    private LinksBean links;
    private String details;
    private String mission_name;

    public int getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(int flight_number) {
        this.flight_number = flight_number;
    }

    public String getLaunch_year() {
        return launch_year;
    }

    public void setLaunch_year(String launch_year) {
        this.launch_year = launch_year;
    }

    public int getLaunch_date_unix() {
        return launch_date_unix;
    }

    public void setLaunch_date_unix(int launch_date_unix) {
        this.launch_date_unix = launch_date_unix;
    }

    public String getLaunch_date_utc() {
        return launch_date_utc;
    }

    public void setLaunch_date_utc(String launch_date_utc) {
        this.launch_date_utc = launch_date_utc;
    }

    public String getLaunch_date_local() {
        return launch_date_local;
    }

    public void setLaunch_date_local(String launch_date_local) {
        this.launch_date_local = launch_date_local;
    }

    public RocketBean getRocket() {
        return rocket;
    }

    public void setRocket(RocketBean rocket) {
        this.rocket = rocket;
    }

    public TelemetryBean getTelemetry() {
        return telemetry;
    }

    public void setTelemetry(TelemetryBean telemetry) {
        this.telemetry = telemetry;
    }

    public ReuseBean getReuse() {
        return reuse;
    }

    public void setReuse(ReuseBean reuse) {
        this.reuse = reuse;
    }

    public LaunchSiteBean getLaunch_site() {
        return launch_site;
    }

    public void setLaunch_site(LaunchSiteBean launch_site) {
        this.launch_site = launch_site;
    }

    public boolean isLaunch_success() {
        return launch_success;
    }

    public void setLaunch_success(boolean launch_success) {
        this.launch_success = launch_success;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMission_name() {
        return mission_name;
    }

    public void setMission_name(String mission_name) {
        this.mission_name = mission_name;
    }

    public static class RocketBean {
        /**
         * rocket_id : falcon9
         * rocket_name : Falcon 9
         * rocket_type : FT
         * first_stage : {"cores":[{"core_serial":"B1041","flight":2,"block":4,"reused":true,"land_success":null,"landing_type":null,"landing_vehicle":null}]}
         * second_stage : {"payloads":[{"payload_id":"Iridium NEXT 5","reused":false,"customers":["Iridium Communications"],"payload_type":"Satellite","payload_mass_kg":9600,"payload_mass_lbs":21164.38,"orbit":"PO"}]}
         */

        private String rocket_id;
        private String rocket_name;
        private String rocket_type;
        private FirstStageBean first_stage;
        private SecondStageBean second_stage;

        public String getRocket_id() {
            return rocket_id;
        }

        public void setRocket_id(String rocket_id) {
            this.rocket_id = rocket_id;
        }

        public String getRocket_name() {
            return rocket_name;
        }

        public void setRocket_name(String rocket_name) {
            this.rocket_name = rocket_name;
        }

        public String getRocket_type() {
            return rocket_type;
        }

        public void setRocket_type(String rocket_type) {
            this.rocket_type = rocket_type;
        }

        public FirstStageBean getFirst_stage() {
            return first_stage;
        }

        public void setFirst_stage(FirstStageBean first_stage) {
            this.first_stage = first_stage;
        }

        public SecondStageBean getSecond_stage() {
            return second_stage;
        }

        public void setSecond_stage(SecondStageBean second_stage) {
            this.second_stage = second_stage;
        }

        public static class FirstStageBean {
            private List<CoresBean> cores;

            public List<CoresBean> getCores() {
                return cores;
            }

            public void setCores(List<CoresBean> cores) {
                this.cores = cores;
            }

            public static class CoresBean {
                /**
                 * core_serial : B1041
                 * flight : 2
                 * block : 4
                 * reused : true
                 * land_success : null
                 * landing_type : null
                 * landing_vehicle : null
                 */

                private String core_serial;
                private int flight;
                private int block;
                private boolean reused;
                private Object land_success;
                private Object landing_type;
                private Object landing_vehicle;

                public String getCore_serial() {
                    return core_serial;
                }

                public void setCore_serial(String core_serial) {
                    this.core_serial = core_serial;
                }

                public int getFlight() {
                    return flight;
                }

                public void setFlight(int flight) {
                    this.flight = flight;
                }

                public int getBlock() {
                    return block;
                }

                public void setBlock(int block) {
                    this.block = block;
                }

                public boolean isReused() {
                    return reused;
                }

                public void setReused(boolean reused) {
                    this.reused = reused;
                }

                public Object getLand_success() {
                    return land_success;
                }

                public void setLand_success(Object land_success) {
                    this.land_success = land_success;
                }

                public Object getLanding_type() {
                    return landing_type;
                }

                public void setLanding_type(Object landing_type) {
                    this.landing_type = landing_type;
                }

                public Object getLanding_vehicle() {
                    return landing_vehicle;
                }

                public void setLanding_vehicle(Object landing_vehicle) {
                    this.landing_vehicle = landing_vehicle;
                }
            }
        }

        public static class SecondStageBean {
            private List<PayloadsBean> payloads;

            public List<PayloadsBean> getPayloads() {
                return payloads;
            }

            public void setPayloads(List<PayloadsBean> payloads) {
                this.payloads = payloads;
            }

            public static class PayloadsBean {
                /**
                 * payload_id : Iridium NEXT 5
                 * reused : false
                 * customers : ["Iridium Communications"]
                 * payload_type : Satellite
                 * payload_mass_kg : 9600
                 * payload_mass_lbs : 21164.38
                 * orbit : PO
                 */

                private String payload_id;
                private boolean reused;
                private String payload_type;
                private int payload_mass_kg;
                private double payload_mass_lbs;
                private String orbit;
                private List<String> customers;

                public String getPayload_id() {
                    return payload_id;
                }

                public void setPayload_id(String payload_id) {
                    this.payload_id = payload_id;
                }

                public boolean isReused() {
                    return reused;
                }

                public void setReused(boolean reused) {
                    this.reused = reused;
                }

                public String getPayload_type() {
                    return payload_type;
                }

                public void setPayload_type(String payload_type) {
                    this.payload_type = payload_type;
                }

                public int getPayload_mass_kg() {
                    return payload_mass_kg;
                }

                public void setPayload_mass_kg(int payload_mass_kg) {
                    this.payload_mass_kg = payload_mass_kg;
                }

                public double getPayload_mass_lbs() {
                    return payload_mass_lbs;
                }

                public void setPayload_mass_lbs(double payload_mass_lbs) {
                    this.payload_mass_lbs = payload_mass_lbs;
                }

                public String getOrbit() {
                    return orbit;
                }

                public void setOrbit(String orbit) {
                    this.orbit = orbit;
                }

                public List<String> getCustomers() {
                    return customers;
                }

                public void setCustomers(List<String> customers) {
                    this.customers = customers;
                }
            }
        }
    }

    public static class TelemetryBean {
        /**
         * flight_club : https://www.flightclub.io/results/?code=IRD5
         */

        private String flight_club;

        public String getFlight_club() {
            return flight_club;
        }

        public void setFlight_club(String flight_club) {
            this.flight_club = flight_club;
        }
    }

    public static class ReuseBean {
        /**
         * core : true
         * side_core1 : false
         * side_core2 : false
         * fairings : false
         * capsule : false
         */

        private boolean core;
        private boolean side_core1;
        private boolean side_core2;
        private boolean fairings;
        private boolean capsule;

        public boolean isCore() {
            return core;
        }

        public void setCore(boolean core) {
            this.core = core;
        }

        public boolean isSide_core1() {
            return side_core1;
        }

        public void setSide_core1(boolean side_core1) {
            this.side_core1 = side_core1;
        }

        public boolean isSide_core2() {
            return side_core2;
        }

        public void setSide_core2(boolean side_core2) {
            this.side_core2 = side_core2;
        }

        public boolean isFairings() {
            return fairings;
        }

        public void setFairings(boolean fairings) {
            this.fairings = fairings;
        }

        public boolean isCapsule() {
            return capsule;
        }

        public void setCapsule(boolean capsule) {
            this.capsule = capsule;
        }
    }

    public static class LaunchSiteBean {
        /**
         * site_id : vafb_slc_4e
         * site_name : VAFB SLC 4E
         * site_name_long : Vandenberg Air Force Base Space Launch Complex 4E
         */

        private String site_id;
        private String site_name;
        private String site_name_long;

        public String getSite_id() {
            return site_id;
        }

        public void setSite_id(String site_id) {
            this.site_id = site_id;
        }

        public String getSite_name() {
            return site_name;
        }

        public void setSite_name(String site_name) {
            this.site_name = site_name;
        }

        public String getSite_name_long() {
            return site_name_long;
        }

        public void setSite_name_long(String site_name_long) {
            this.site_name_long = site_name_long;
        }
    }

    public static class LinksBean {
        /**
         * mission_patch : https://i.imgur.com/QUSoLHy.png
         * reddit_campaign : https://www.reddit.com/r/spacex/comments/82njj5/iridium_next_constellation_mission_5_launch/
         * reddit_launch : https://www.reddit.com/r/spacex/comments/88184i/rspacex_iridium_next_5_official_launch_discussion/
         * reddit_recovery : null
         * reddit_media : https://www.reddit.com/r/spacex/comments/88114l/rspacex_iridium5_media_thread_videos_images_gifs/
         * presskit : http://www.spacex.com/sites/spacex/files/iridium-5_press_kit.pdf
         * article_link : https://spaceflightnow.com/2018/03/30/iridium-messaging-network-gets-another-boost-from-spacex/
         * video_link : https://www.youtube.com/watch?v=mp0TW8vkCLg
         */

        private String mission_patch;
        private String mission_patch_small;
        private String reddit_campaign;
        private String reddit_launch;
        private Object reddit_recovery;
        private String reddit_media;
        private String presskit;
        private String article_link;
        private String video_link;

        public String getIssion_patch() {
            return mission_patch;
        }

        public void setIssion_patch(String ission_patch) {
            mission_patch = ission_patch;
        }

        public String getReddit_campaign() {
            return reddit_campaign;
        }

        public void setReddit_campaign(String reddit_campaign) {
            this.reddit_campaign = reddit_campaign;
        }

        public String getReddit_launch() {
            return reddit_launch;
        }

        public void setReddit_launch(String reddit_launch) {
            this.reddit_launch = reddit_launch;
        }

        public Object getReddit_recovery() {
            return reddit_recovery;
        }

        public void setReddit_recovery(Object reddit_recovery) {
            this.reddit_recovery = reddit_recovery;
        }

        public String getReddit_media() {
            return reddit_media;
        }

        public void setReddit_media(String reddit_media) {
            this.reddit_media = reddit_media;
        }

        public String getPresskit() {
            return presskit;
        }

        public void setPresskit(String presskit) {
            this.presskit = presskit;
        }

        public String getArticle_link() {
            return article_link;
        }

        public void setArticle_link(String article_link) {
            this.article_link = article_link;
        }

        public String getVideo_link() {
            return video_link;
        }

        public void setVideo_link(String video_link) {
            this.video_link = video_link;
        }

        public String getMission_patch_small() {
            return mission_patch_small;
        }

        public void setMission_patch_small(String mission_patch_small) {
            this.mission_patch_small = mission_patch_small;
        }
    }
}
