/*
* fragment shader template
*/

uniform vec3 m_sundir;
uniform vec3 m_moondir;
uniform vec4 m_suncolor;
uniform vec4 m_mooncolor;
uniform vec4 m_skycolor;

varying vec3 direction;

void main() {
    vec3 dir = normalize(direction);
    vec3 sundir = normalize(m_sundir);
    vec3 moondir = normalize(m_moondir);
    vec3 down = vec3(0.0, -1.0, 0.0);

    vec4 suncolornorm = normalize(m_suncolor);

    float sunfac = dot(dir, sundir);
    vec4 sunpart = m_suncolor * (sunfac - 0.7);
    sunpart = clamp(sunpart, 0.0, 1.0);

    float moonfac = dot(dir, moondir);
    vec4 moonpart = m_mooncolor * (moonfac - 0.7);
    moonpart = clamp(moonpart, 0.0, 1.0);

    float afterglow = clamp(0.5 - abs(dot(down, sundir)), 0.0, 1.0);
    afterglow *= clamp(0.7 - abs(dot(down, dir)), 0.0, 1.0) * 3.0f;
    vec4 afterglowpart = suncolornorm * suncolornorm * afterglow;

    float skybrightness = dot(down, sundir) + 0.1f;
    vec4 skypart = m_skycolor * skybrightness;
    skypart = clamp(skypart, 0.0, 1.0);

    vec4 finalsky = sunpart + skypart + afterglowpart + moonpart;

    if(sunfac > 0.995) finalsky = m_suncolor;
    if(moonfac > 0.998) finalsky = m_mooncolor;

    gl_FragColor = finalsky;
}


