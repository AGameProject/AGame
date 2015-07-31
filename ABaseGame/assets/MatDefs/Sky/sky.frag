uniform vec3 m_sundir;
uniform vec3 m_moondir;
uniform vec4 m_suncolor;
uniform vec4 m_mooncolor;
uniform vec4 m_skycolor;

uniform bool m_spheremap;
uniform sampler2D m_starmap;

varying vec3 direction;

void main() {
    vec3 dir = normalize(direction);
    vec3 sundir = normalize(m_sundir);
    vec3 moondir = normalize(m_moondir);
    vec3 down = vec3(0.0, -1.0, 0.0);

    float sunfac = dot(dir, sundir);
    vec4 sunpart = m_suncolor * (sunfac - 0.7);
    sunpart = clamp(sunpart, 0.0, 1.0);

    float moonfac = dot(dir, moondir);
    vec4 moonpart = m_mooncolor * (moonfac - 0.7);
    moonpart = clamp(moonpart, 0.0, 1.0);

    float afterglow = clamp(0.5 - abs(dot(down, sundir)), 0.0, 1.0);
    afterglow *= clamp(0.7 - abs(dot(down, dir)), 0.0, 1.0) * 3.0;
    vec4 afterglowpart = m_suncolor * m_suncolor * afterglow;

    float skybrightness = dot(down, sundir) + 0.1;
    vec4 skypart = m_skycolor * skybrightness;
    skypart = clamp(skypart, 0.0, 1.0);

    float starbrightness = dot(down, moondir) + 0.1;
    starbrightness = clamp(starbrightness, 0.0, 1.0);
    vec4 stars = vec4(0.0);
    if(starbrightness > 0.0) {
        vec2 longlat = vec2((atan(dir.y, dir.x) / 3.1415926 + 1.0) * 0.5, (asin(dir.z) / 3.1415926 + 0.5));
        stars = texture2D(m_starmap, longlat) * starbrightness;
    }

    vec4 finalsky = sunpart + skypart + afterglowpart + moonpart + stars;

    if(sunfac > 0.995) finalsky = m_suncolor;
    if(moonfac > 0.998) finalsky = m_mooncolor;

    gl_FragColor = finalsky;
}


