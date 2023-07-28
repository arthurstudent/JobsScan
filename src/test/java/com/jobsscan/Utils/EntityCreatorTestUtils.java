package com.jobsscan.Utils;

import com.jobsscan.domain.LabelEntity;
import com.jobsscan.domain.LocationEntity;
import com.jobsscan.domain.VacancyEntity;

public class EntityCreatorTestUtils {

    public static String vacancyPublicId = "0192099974";
    public static String companyName = "AnswersNow";
    public static String companyUrl = "https://boards.greenhouse.io/answersnow/jobs/4025946007?utm_source=Techstars+job+board&utm_medium=getro.com&gh_src=Techstars+job+board";
    public static String description = "Short description";
    public static long postedDate = 1690478964;
    public static String logoLink = "https://cdn.filestackcontent.com/output=f:webp,t:true,q:80,c:true/cache=expiry:max/resize=w:340/UqSz4TFJQ4eTm7DfWFr4";
    public static String jobTitle = "Vice President of Growth & Partnerships";

    public static String laborFuncPublicId = "3558508423";
    public static String laborFuncName = "Sales & Business Development";

    public static String locationPublicId = "0437884185";
    public static String country = "USA";
    public static String city = "Austin";
    public static String remote = "Remote";

    public static VacancyEntity buildVacationEntity() {

        VacancyEntity vacancyEntity = new VacancyEntity();
        vacancyEntity.addLocation(buildLocationEntity());
        vacancyEntity.addLabel(buildLabelEntity());
        vacancyEntity.setCompanyName(EntityCreatorTestUtils.companyName);
        vacancyEntity.setDescription(EntityCreatorTestUtils.description);
        vacancyEntity.setPostedDate(EntityCreatorTestUtils.postedDate);
        vacancyEntity.setJobTitle(EntityCreatorTestUtils.jobTitle);
        vacancyEntity.setLogoLink(EntityCreatorTestUtils.logoLink);
        vacancyEntity.setPublicId(EntityCreatorTestUtils.vacancyPublicId);
        vacancyEntity.setCompanyUrl(EntityCreatorTestUtils.companyUrl);

        return vacancyEntity;
    }

    public static LocationEntity buildLocationEntity() {

        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setCity(EntityCreatorTestUtils.city);
        locationEntity.setCountry(EntityCreatorTestUtils.country);
        locationEntity.setPublicId(EntityCreatorTestUtils.locationPublicId);
        locationEntity.setRemote(EntityCreatorTestUtils.remote);

        return locationEntity;
    }

    public static LabelEntity buildLabelEntity() {

        LabelEntity labelEntity = new LabelEntity();
        labelEntity.setPublicId(EntityCreatorTestUtils.laborFuncPublicId);
        labelEntity.setLaborFunction(EntityCreatorTestUtils.laborFuncName);
        return labelEntity;
    }
}
